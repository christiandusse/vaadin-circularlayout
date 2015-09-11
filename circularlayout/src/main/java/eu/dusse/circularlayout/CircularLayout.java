package eu.dusse.circularlayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;

import eu.dusse.circularlayout.client.CircularLayoutState;
import eu.dusse.circularlayout.client.CircularLayoutState.Direction;
import eu.dusse.circularlayout.client.CircularLayoutState.StartPosition;

@SuppressWarnings("serial")
public class CircularLayout extends AbstractComponentContainer
{

    private final List<Component> children;


    public CircularLayout()
    {
        children = new ArrayList<Component>();
    }


    public CircularLayout(Component... components)
    {
        this();
        addComponents(components);
    }


    @Override
    public void addComponent(Component c)
    {
        children.add(c);
        super.addComponent(c);
        markAsDirty();
    }


    @Override
    public void removeComponent(Component c)
    {
        children.remove(c);
        super.removeComponent(c);
        markAsDirty();
    }


    public void replaceComponent(Component oldComponent, Component newComponent)
    {
        int index = children.indexOf(oldComponent);
        if (index != -1)
        {
            children.remove(index);
            children.add(index, newComponent);
            fireComponentDetachEvent(oldComponent);
            fireComponentAttachEvent(newComponent);
            markAsDirty();
        }
    }


    @Override
    protected CircularLayoutState getState()
    {
        return (CircularLayoutState) super.getState();
    }


    public int getComponentCount()
    {
        return children.size();
    }


    public Iterator<Component> iterator()
    {
        return children.iterator();
    }


    public int getDiameter()
    {
        return getState().diameter;
    }


    public void setDiameter(int diameter)
    {
        getState().diameter = diameter;
        markAsDirty();
    }


    public boolean isRotateElements()
    {
        return getState().rotateElements;
    }


    public void setRotateElements(boolean rotateElements)
    {
        getState().rotateElements = rotateElements;
        markAsDirty();
    }


    public StartPosition getStartPosition()
    {
        return getState().startPosition;
    }


    public void setStartPosition(StartPosition startPosition)
    {
        getState().startPosition = startPosition;
        markAsDirty();
    }


    public Direction getDirection()
    {
        return getState().direction;
    }


    public void setDirection(Direction direction)
    {
        getState().direction = direction;
        markAsDirty();
    }

}
