package com.dyoung.carpool.node.greendao.model;

import org.greenrobot.greendao.converter.PropertyConverter;
import com.dyoung.carpool.node.greendao.model.NodeType;

public class NoteTypeConverter implements PropertyConverter<NodeType, String> {
    @Override
    public NodeType convertToEntityProperty(String databaseValue) {
        return NodeType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NodeType entityProperty) {
        return entityProperty.name();
    }
}
