package eu.dusse.circularlayout.demo;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

import eu.dusse.circularlayout.CircularLayout;
import eu.dusse.circularlayout.client.CircularLayoutState.Direction;
import eu.dusse.circularlayout.client.CircularLayoutState.StartPosition;

@Theme("demo")
@Title("Circular layout Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "eu.dusse.circularlayout.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet
    {
    }

    private static final int INIT_DIAMETER = 200;
    private static final boolean INIT_ROTATE = true;
    private static final StartPosition INIT_STARTPOSITION = StartPosition.TOP;
    private static final Direction INIT_DIRECTION = Direction.CLOCKWISE;


    @Override
    protected void init(VaadinRequest request)
    {
        final AtomicInteger counter = new AtomicInteger(0);

        final CircularLayout circularLayout = new CircularLayout();
        circularLayout.setDiameter(INIT_DIAMETER);
        circularLayout.setRotateElements(INIT_ROTATE);
        circularLayout.setStartPosition(INIT_STARTPOSITION);
        circularLayout.setSizeFull();

        Button addButton = new Button();
        addButton.setCaption("Add element");
        addButton.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                final String number = String.valueOf(counter.incrementAndGet());
                final Button button = createElement(number);
                circularLayout.addComponent(button);
            }
        });

        final Slider diameterSlider = new Slider();
        diameterSlider.setCaption("Diameter");
        diameterSlider.setMin(50.0);
        diameterSlider.setMax(400.0);
        diameterSlider.setValue((double) INIT_DIAMETER);
        diameterSlider.addValueChangeListener(new ValueChangeListener()
        {
            @Override
            public void valueChange(ValueChangeEvent event)
            {
                circularLayout.setDiameter(diameterSlider.getValue().intValue());
            }
        });

        final CheckBox rotateCheckBox = new CheckBox();
        rotateCheckBox.setCaption("Rotate elements");
        rotateCheckBox.setValue(INIT_ROTATE);
        rotateCheckBox.addValueChangeListener(new ValueChangeListener()
        {
            @Override
            public void valueChange(ValueChangeEvent event)
            {
                circularLayout.setRotateElements(rotateCheckBox.getValue());
            }
        });

        final OptionGroup startPositionField = new OptionGroup();
        startPositionField.setCaption("Start position (offset)");
        startPositionField.setMultiSelect(false); // radio group
        startPositionField.setNullSelectionAllowed(false);
        startPositionField.addItems(Arrays.asList(StartPosition.values()));
        startPositionField.setValue(INIT_STARTPOSITION);
        startPositionField.addValueChangeListener(new ValueChangeListener()
        {
            @Override
            public void valueChange(ValueChangeEvent event)
            {
                circularLayout.setStartPosition((StartPosition) startPositionField.getValue());
            }
        });

        final OptionGroup directionField = new OptionGroup();
        directionField.setCaption("Direction");
        directionField.setMultiSelect(false); // radio group
        directionField.setNullSelectionAllowed(false);
        directionField.addItems(Arrays.asList(Direction.values()));
        directionField.setValue(INIT_DIRECTION);
        directionField.addValueChangeListener(new ValueChangeListener()
        {
            @Override
            public void valueChange(ValueChangeEvent event)
            {
                circularLayout.setDirection((Direction) directionField.getValue());
            }
        });

        HorizontalLayout actionLayout = new HorizontalLayout();
        actionLayout.setSpacing(true);
        actionLayout.addComponents(addButton, diameterSlider, rotateCheckBox, startPositionField, directionField);

        HorizontalSplitPanel horSplit = new HorizontalSplitPanel(circularLayout, new CssLayout());
        horSplit.setSplitPosition(50, Unit.PERCENTAGE);
        horSplit.setSizeFull();

        VerticalSplitPanel main = new VerticalSplitPanel(horSplit, actionLayout);
        main.setSplitPosition(300, Unit.PIXELS);
        main.setSizeFull();

        setContent(main);
        setSizeFull();
    }


    private static Button createElement(final String number)
    {
        final Button button = new Button();
        button.setCaption(number);
        button.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                Notification.show("clicked");
                if (button.getCaption().equals(number))
                {
                    button.setCaption("clicked");
                }
                else
                {
                    button.setCaption(number);
                }
            }
        });
        return button;
    }

}
