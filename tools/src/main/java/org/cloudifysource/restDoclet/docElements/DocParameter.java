package org.cloudifysource.restDoclet.docElements;

import java.util.List;

import org.cloudifysource.restDoclet.constants.RestDocConstants.DocAnnotationTypes;

import com.sun.javadoc.Type;

public class DocParameter {
	Type type;
	String name;
	String description;
	String location;
	
	List<DocAnnotation> annotations;
	DocRequestParamAnnotation requestParamAnnotation;

	public DocParameter(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean isRequired() {
		if(requestParamAnnotation != null)
			return requestParamAnnotation.isRequierd() == null ? Boolean.FALSE : requestParamAnnotation.isRequierd();
		return true;
	}
	public List<DocAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<DocAnnotation> annotations) {
		this.annotations = annotations;
		setAnnotationsAttributes();
	}
	public String getLocation() {
		return location;
	}

	public String getDefaultValue() {
		if(requestParamAnnotation != null )
			return requestParamAnnotation.getDefaultValue();
		return null;
	}
	public DocRequestParamAnnotation getRequestParamAnnotation() {
		return requestParamAnnotation;
	}
	
	private void setAnnotationsAttributes() {
		String location = "";
		for (DocAnnotation docAnnotation : annotations) {
			String annotationName = docAnnotation.getName();
			switch (DocAnnotationTypes.fromName(annotationName)) {
			case REQUEST_PARAM:
				requestParamAnnotation = (DocRequestParamAnnotation) docAnnotation;
			case PATH_VARIABLE:
			case REQUEST_BODY:
				if(!location.isEmpty())
					location += " or ";
				location += annotationName;
				break;
			default:
				throw new IllegalArgumentException("Unsupported parameter annotation - " + annotationName);
			}
		}
		this.location = location;
	}

	@Override
	public String toString() {
		String str = "Parameter[";
		if(annotations !=null) {
			if(annotations.size() == 1)
				str += annotations.get(0) + ", "; 
			else
				str += annotations + ", ";
		}
		str += "type = " + type + ", name = " + name;
		if(description != null)
			str += description;
		return str + "]";
	}

}