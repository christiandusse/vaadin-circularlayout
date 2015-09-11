package eu.dusse.circularlayout.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.client.ui.layout.ElementResizeEvent;
import com.vaadin.client.ui.layout.ElementResizeListener;
import com.vaadin.shared.ui.Connect;

import eu.dusse.circularlayout.CircularLayout;

@SuppressWarnings("serial")
@Connect(CircularLayout.class)
public class CircularLayoutConnector extends AbstractComponentContainerConnector
{
    
    // TODO performance

    private final ElementResizeListener resizeListener = new ElementResizeListener()
    {
        @Override
        public void onElementResize(ElementResizeEvent e)
        {
            updateChildren();
        }
    };


    @Override
    protected void init()
    {
        super.init();

        getWidget().client = getConnection();

        getLayoutManager().addElementResizeListener(getWidget().getElement(), resizeListener);
    }


    @Override
    public void onUnregister()
    {
        super.onUnregister();

        getLayoutManager().removeElementResizeListener(getWidget().getElement(), resizeListener);
    }


    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event)
    {
        for (ComponentConnector oldChild : event.getOldChildren())
        {
            getLayoutManager().removeElementResizeListener(oldChild.getWidget().getElement(), resizeListener);
        }
        for (ComponentConnector newChild : getChildComponents())
        {
            getLayoutManager().addElementResizeListener(newChild.getWidget().getElement(), resizeListener);
        }

        updateChildren();
    }


    @Override
    public CircularLayoutWidget getWidget()
    {
        return (CircularLayoutWidget) super.getWidget();
    }


    public void updateCaption(ComponentConnector connector)
    {
        // TODO Implement caption handling
    }


    @Override
    public CircularLayoutState getState()
    {
        return (CircularLayoutState) super.getState();
    }


    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent)
    {
        super.onStateChanged(stateChangeEvent);

        updateChildren();
    }


    private void updateChildren()
    {
        List<Widget> childWidgets = new ArrayList<Widget>();
        for (ComponentConnector connector : getChildComponents())
        {
            childWidgets.add(connector.getWidget());
        }
        getWidget().setContent(childWidgets);

        getWidget().setChildrenStyle(getState().diameter, getState().rotateElements, getState().startPosition,
                getState().direction);
    }

}
