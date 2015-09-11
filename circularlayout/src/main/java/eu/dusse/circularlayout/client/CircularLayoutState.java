package eu.dusse.circularlayout.client;

import com.vaadin.shared.AbstractComponentState;

@SuppressWarnings("serial")
public class CircularLayoutState extends AbstractComponentState
{

    public int diameter = 100;
    public boolean rotateElements = true;
    public StartPosition startPosition = StartPosition.TOP;
    public Direction direction = Direction.CLOCKWISE;

    public enum StartPosition
    {

        TOP(Math.PI / 2.0, 90), //
        RIGHT(0.0, 0), //
        BOTTOM(Math.PI * 1.5, 270), //
        LEFT(Math.PI, 180); //

        private final double diffRad;
        private final int diffDeg;


        private StartPosition(double diffRad, int diffDeg)
        {
            this.diffRad = diffRad;
            this.diffDeg = diffDeg;
        }


        public double getDiffRad()
        {
            return diffRad;
        }


        public int getDiffDeg()
        {
            return diffDeg;
        }

    }

    public enum Direction
    {

        CLOCKWISE, //
        ANTI_CLOCKWISE; //

    }

}
