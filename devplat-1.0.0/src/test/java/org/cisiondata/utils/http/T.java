package org.cisiondata.utils.http;

import java.util.ArrayList;
import java.util.List;

import org.cisiondata.modules.auth.entity.FieldEncryptType;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.utils.json.GsonUtils;

public class T {

	public static void main(String[] args) {
		ResourceInterfaceField field = new ResourceInterfaceField();
		field.setId(1L);
		field.setFieldEN("name");
		field.setEncryptType(FieldEncryptType.NO_ENCRYPT.value());
		List<ResourceInterfaceField> list = new ArrayList<ResourceInterfaceField>();
		list.add(field);
		String json = GsonUtils.builder().toJson(list, List.class);
		System.out.println(json);
		List<ResourceInterfaceField> newlist = GsonUtils.fromJsonToList(json, ResourceInterfaceField.class);
		System.out.println("size: " + newlist.size());
		for (ResourceInterfaceField l : newlist) {
			System.out.println(l);
			System.out.println(l.getId());
			System.out.println(l.getFieldEN());
			System.out.println(l.getEncryptType());
		}
	}
	
}
