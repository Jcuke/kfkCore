package com.jp.base.tool.doc;

import java.util.List;

public class Desc {
	public static String descClass(ClassInfo ci, String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n" + ci.c.getName() + "\n");
		for (int i = 0; i < ci.methods.size(); i++) {
			MethodInfo mi = ci.methods.get(i);
			/*
			 * sb.append("方法名:" + mi.name + "\n"); sb.append("请求参数:\n" +
			 * descFields(mi.argFields, prefix) + "\n"); sb.append("返回参数:\n" +
			 * descFields(mi.returnFields, prefix) + "\n");
			 */
			sb.append(descMethod(mi, prefix));
		}
		return sb.toString();
	}

	public static String descMethod(MethodInfo mi, String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append("" + mi.name + "\n");
		// sb.append("请求参数:\n" + descFields(mi.argFields, prefix) + "\n");
		sb.append("请求参数:\n");
		for (int i = 0; i < mi.args.size(); i++) {
			ArgInfo arg = mi.args.get(i);
			sb.append("参数" + i + ":");
			/*
			 * if(arg.isPrimitive()){ sb.append(arg.getType().getName()); }else
			 * if(arg.isMap()){ sb.append("{}//map"); }else{
			 * sb.append(descFields(arg.argFields, prefix)); }
			 */
			sb.append(descArg(arg, prefix));
			sb.append("\n");
		}
		// sb.append("返回参数:\n" + descFields(mi.returnFields, prefix) + "\n");
		sb.append("返回参数:\n" + descArg(mi.getReturnInfo(), prefix) + "\n");
		return sb.toString();
	}

	public static String descArg(ArgInfo arg, String prefix) {
		StringBuffer sb = new StringBuffer();
		if (arg.isPrimitive()) {
			sb.append(arg.getType().getName());
		} else if (arg.isMap()) {
			sb.append("{}//map");
		} else {
			sb.append(descFields(arg.argFields, prefix));
		}
		return sb.toString();
	}

	public static String descFields(List<FieldInfo> fields, String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix + "{\n");
		for (int i = 0; i < fields.size(); i++) {
			FieldInfo field = fields.get(i);
			if (i > 0){
				sb.append(",");
				if(field.name.equals("access_token")){
					sb.append("//token标识");
				}
				sb.append("\n");
			}
			sb.append(desc(field, prefix + "\t"));

		}
		sb.append("\n" + prefix + "}");
		return sb.toString();
	}

	public static String desc(FieldInfo field) {
		return desc(field, "");
	}

	public static String desc(FieldInfo field, String prefix) {
		if (prefix == null)
			prefix = "";
		StringBuffer sb = new StringBuffer();
		if(null!=field.name &&"body".equals(field.name))
		{
			sb.append(prefix  + "p:");
		}
		else
		{
			
			sb.append(prefix + field.name + ":");
		}
		if (field.isprim) {
			sb.append(prefix + field.type);
		} else if (field.isarray) {
			sb.append("[//数组对象的数据结构\n");
			sb.append(descFields(field.subFields, prefix));

			sb.append("\n" + prefix + "]");
		} else {
			// sb.append("{\n");
			sb.append(descFields(field.subFields, prefix));
			// sb.append("\n"+prefix + "}");
		}

		return sb.toString();
	}
}
