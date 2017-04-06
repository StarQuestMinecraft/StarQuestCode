package net.homeip.hall.sqnetevents.packet;

import java.io.Serializable;
import java.util.HashMap;

public class Data implements Serializable {

    private static final long serialVersionUID = 1;
    //map of all added items
    private final HashMap<String, DataObject> map = new HashMap<>();
    //Add key-value mapped pairs
    public void addString(String id, String data){
        map.put(id, new DataObject(DataType.STRING, data));
    }

    public void addInt(String id, int data){
        map.put(id, new DataObject(DataType.INT, data));
    }

    public void addDouble(String id, double data){
        map.put(id, new DataObject(DataType.DOUBLE, data));
    }

    public void addFloat(String id, float data){
        map.put(id, new DataObject(DataType.FLOAT, data));
    }

    public void addBoolean(String id, boolean data){
        map.put(id, new DataObject(DataType.BOOLEAN, data));
    }

    public void addLong(String id, long data){
        map.put(id, new DataObject(DataType.LONG, data));
    }

    public void addByte(String id, byte data){
        map.put(id, new DataObject(DataType.BYTE, data));
    }

    public void addShort(String id, short data){
        map.put(id, new DataObject(DataType.SHORT, data));
    }

    public void addObject(String id, Object data){
        map.put(id, new DataObject(DataType.OBJECT, data));
    }
    //map keys to values
    public String getString(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.STRING)) throw new RuntimeException("Object id " + id + " is not a String DataObject!");
        return ((String) object.getData());
    }

    public int getInt(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.INT)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((int) object.getData());
    }

    public double getDouble(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.DOUBLE)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((double) object.getData());
    }

    public float getFloat(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.FLOAT)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((float) object.getData());
    }

    public boolean getBoolean(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.BOOLEAN)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((boolean) object.getData());
    }

    public long getLong(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.LONG)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((long) object.getData());
    }

    public byte getByte(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.BYTE)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((byte) object.getData());
    }

    public short getShort(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        if(!object.getType().equals(DataType.SHORT)) throw new RuntimeException("Type and data do not match in DataObject!");
        return ((short) object.getData());
    }

    @SuppressWarnings("unchecked")
	public <T> T getObject(String id){
        DataObject object = map.get(id);
        if(object == null) throw new RuntimeException("No object with id '"+id+"' found!");
        //noinspection unchecked
        return (T) object.getData();
    }

    private class DataObject implements Serializable {
    	
        private static final long serialVersionUID = 1;
    	
        private final DataType type;
        private final Object data;

        private DataObject(DataType type, Object data) {
            this.type = type;
            this.data = data;
        }

        private DataType getType(){
            return type;
        }

        private Object getData(){
            return data;
        }

    }

    private enum DataType implements Serializable {

        BYTE, SHORT, INT, LONG, DOUBLE, FLOAT, BOOLEAN, OBJECT, STRING

    }

}