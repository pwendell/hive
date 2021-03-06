/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf;

import java.sql.Date;
import java.sql.Timestamp;

import junit.framework.TestCase;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFDateAdd;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class TestGenericUDFDateAdd extends TestCase {
  public void testStringToDate() throws HiveException {
    GenericUDFDateAdd udf = new GenericUDFDateAdd();
    ObjectInspector valueOI1 = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    ObjectInspector valueOI2 = PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    ObjectInspector[] arguments = {valueOI1, valueOI2};

    udf.initialize(arguments);
    DeferredObject valueObj1 = new DeferredJavaObject(new Text("2009-07-20 04:17:52"));
    DeferredObject valueObj2 = new DeferredJavaObject(new Integer("2"));
    DeferredObject[] args = {valueObj1, valueObj2};
    Text output = (Text) udf.evaluate(args);

    assertEquals("date_add() test for STRING failed ", "2009-07-22", output.toString());

    // Test with null args
    args = new DeferredObject[] { new DeferredJavaObject(null), valueObj2 };
    assertNull("date_add() 1st arg null", udf.evaluate(args));

    args = new DeferredObject[] { valueObj1, new DeferredJavaObject(null) };
    assertNull("date_add() 2nd arg null", udf.evaluate(args));

    args = new DeferredObject[] { new DeferredJavaObject(null), new DeferredJavaObject(null) };
    assertNull("date_add() both args null", udf.evaluate(args));
  }

  public void testTimestampToDate() throws HiveException {
    GenericUDFDateAdd udf = new GenericUDFDateAdd();
    ObjectInspector valueOI1 = PrimitiveObjectInspectorFactory.writableTimestampObjectInspector;
    ObjectInspector valueOI2 = PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    ObjectInspector[] arguments = {valueOI1, valueOI2};

    udf.initialize(arguments);
    DeferredObject valueObj1 = new DeferredJavaObject(new TimestampWritable(new Timestamp(109, 06,
        20, 4, 17, 52, 0)));
    DeferredObject valueObj2 = new DeferredJavaObject(new Integer("3"));
    DeferredObject[] args = {valueObj1, valueObj2};
    Text output = (Text) udf.evaluate(args);

    assertEquals("date_add() test for TIMESTAMP failed ", "2009-07-23", output.toString());

    // Test with null args
    args = new DeferredObject[] { new DeferredJavaObject(null), valueObj2 };
    assertNull("date_add() 1st arg null", udf.evaluate(args));

    args = new DeferredObject[] { valueObj1, new DeferredJavaObject(null) };
    assertNull("date_add() 2nd arg null", udf.evaluate(args));

    args = new DeferredObject[] { new DeferredJavaObject(null), new DeferredJavaObject(null) };
    assertNull("date_add() both args null", udf.evaluate(args));
  }

  public void testDateWritablepToDate() throws HiveException {
    GenericUDFDateAdd udf = new GenericUDFDateAdd();
    ObjectInspector valueOI1 = PrimitiveObjectInspectorFactory.writableDateObjectInspector;
    ObjectInspector valueOI2 = PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    ObjectInspector[] arguments = {valueOI1, valueOI2};


    udf.initialize(arguments);
    DeferredObject valueObj1 = new DeferredJavaObject(new DateWritable(new Date(109, 06, 20)));
    DeferredObject valueObj2 = new DeferredJavaObject(new Integer("4"));
    DeferredObject[] args = {valueObj1, valueObj2};
    Text output = (Text) udf.evaluate(args);

    assertEquals("date_add() test for DATEWRITABLE failed ", "2009-07-24", output.toString());

    // Test with null args
    args = new DeferredObject[] { new DeferredJavaObject(null), valueObj2 };
    assertNull("date_add() 1st arg null", udf.evaluate(args));

    args = new DeferredObject[] { valueObj1, new DeferredJavaObject(null) };
    assertNull("date_add() 2nd arg null", udf.evaluate(args));

    args = new DeferredObject[] { new DeferredJavaObject(null), new DeferredJavaObject(null) };
    assertNull("date_add() both args null", udf.evaluate(args));
  }

}
