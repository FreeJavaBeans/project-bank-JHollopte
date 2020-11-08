package com.revature.menus;

public class FancyBorder implements DisplayableBuilder {

	private Displayable d;
	
	
	
	public Displayable enhancedDisplay() {
		String base = d.display();
		String[] lines = base.split("\n");
		String header = "";
		String footer = "";
		int maxSize = 20;
		for(String s : lines) {
			if(s.length() > maxSize) {
				maxSize = s.length();
			}
		}
		for(int i = 0; i < maxSize; i++) {
			if(i==Math.floor(maxSize/2)) {
				header += "Welcome to JHBank";
				footer += "-----------------";
			}
			header += "~";
			footer += "-";
		}
		String ret = header + "\n" + base + footer;
		return ()->ret;
	}



	public FancyBorder(Displayable d) {
		super();
		this.d = d;
	}

}
