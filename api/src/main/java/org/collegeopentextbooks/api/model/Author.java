package org.collegeopentextbooks.api.model;

/**
 * A resource's author
 * @author steve.perkins
 *
 */
public class Author extends AbstractModelObject {
	private String name;
	private String searchName;
	
	public Author() {}
	public Author(String name) {
		this.setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(null != name)
			setSearchName(name.toLowerCase());
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
