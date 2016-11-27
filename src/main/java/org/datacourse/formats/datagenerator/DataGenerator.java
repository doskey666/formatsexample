package org.datacourse.formats.datagenerator;

import org.datacourse.formats.models.SomeObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doskey on 27/11/2016.
 */
public class DataGenerator {

    public static List<SomeObject> generateObjects(int numberOfObjects) {
        List<SomeObject> objects = new ArrayList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            SomeObject someObject = new SomeObject();
            someObject.setSomeIntMember(i);
            someObject.setSomeStringMember("FOO BAR " + i);
            objects.add(someObject);
        }
        return objects;
    }
}
