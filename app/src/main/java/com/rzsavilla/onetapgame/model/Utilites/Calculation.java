package com.rzsavilla.onetapgame.model.Utilites;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * Singleton Class
 * Contains calculation functions
 * @author rzsavilla
 */
public class Calculation {

    /**
     * Instance of the object
     */
    private static Calculation m_Calculation = new Calculation();

    /**
     * Private Default Constructor
     */
    private Calculation() {};       //Constructor

    /**
     * Returns the instanciated Calculation Object
     * @return
     */
    public static Calculation getInstance() {
        return m_Calculation;
    }

    /**
     * Returns the distance between two vectors
     * @param v1 First vector
     * @param v2 Second vector
     * @return The Distance between the two vectors
     */
    public float getDistance(Vector2D v1, Vector2D v2) {
        return (float)Math.sqrt(Math.pow(v2.x - v1.x,2) - Math.pow(v2.y - v1.y,2));
    }

    /**
     * Code returns a complete clone of an object.
     * Code is from http://alvinalexander.com/java/java-deep-clone-example-source-code
     * @param object The Object to clone
     * @return Object clone
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * http://www.javaworld.com/article/2077578/learn-java/java-tip-76--an-alternative-to-the-deep-copy-technique.html
     * @param oldObj
     * @return
     * @throws Exception
     */
    static public Object deepCopy(Object oldObj) throws Exception
    {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream(); // A
            oos = new ObjectOutputStream(bos); // B
            // serialize and pass the object
            oos.writeObject(oldObj);   // C
            oos.flush();               // D
            ByteArrayInputStream bin =
                    new ByteArrayInputStream(bos.toByteArray()); // E
            ois = new ObjectInputStream(bin);                  // F
            // return the new object
            return ois.readObject(); // G
        }
        catch(Exception e)
        {
            System.out.println("Exception in ObjectCloner = " + e);
            throw(e);
        }
        finally
        {
            oos.close();
            ois.close();
        }
    }
}
