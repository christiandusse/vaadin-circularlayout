package eu.dusse.circularlayout.client;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.LayoutManager;

import eu.dusse.circularlayout.client.CircularLayoutState.StartPosition;

public class CircularLayoutWidget extends HTML
{

    private static final String CLASSNAME = "v-circularlayout";

    public ApplicationConnection client;


    public CircularLayoutWidget()
    {
        setStyleName(CLASSNAME);
    }


    public void setContent(List<Widget> children)
    {
        getElement().removeAllChildren();

        for (Widget child : children)
        {
            getElement().appendChild(child.getElement());
        }
    }


    private double getEffectiveHeight()
    {
        return LayoutManager.get(client).getOuterHeightDouble(getElement());
    }


    private double getEffectiveWidth()
    {
        return LayoutManager.get(client).getOuterWidthDouble(getElement());
    }


    public void setChildrenStyle(int diameter, boolean rotateElements, StartPosition startPosition,
            CircularLayoutState.Direction direction)
    {
        int nofChildren = getElement().getChildCount();

        Element parent = getElement();
        Element child = parent.getFirstChildElement();

        for (int i = 0; i < nofChildren; i++)
        {
            assert(child != null);

            double alphaRad = ((2.0 * Math.PI * i) / nofChildren);
            double alphaDeg = ((360.0 * i) / nofChildren);

            if (direction == CircularLayoutState.Direction.CLOCKWISE)
            {
                alphaRad *= -1;
                alphaDeg *= -1;
            }

            alphaRad += startPosition.getDiffRad();
            alphaDeg += startPosition.getDiffDeg();

            double left = (getEffectiveWidth() / 2) + ((Math.cos(alphaRad) * diameter) / 2)
                    - (child.getOffsetWidth() / 2);
            double top = (getEffectiveHeight() / 2) - ((Math.sin(alphaRad) * diameter) / 2)
                    - (child.getOffsetHeight() / 2);

            child.getStyle().setLeft(left, Unit.PX);
            child.getStyle().setTop(top, Unit.PX);
            child.getStyle().setPosition(Position.ABSOLUTE);
            if (rotateElements)
            {
                child.getStyle().setProperty("transform", "rotate(" + (90.0 - alphaDeg) + "deg)");
            }
            else
            {
                child.getStyle().clearProperty("transform");
            }

            child = child.getNextSiblingElement();
        }

    }

}
