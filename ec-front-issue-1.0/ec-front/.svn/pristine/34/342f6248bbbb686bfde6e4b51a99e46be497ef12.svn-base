package com.winxuan.ec.front.controller.cm;

/**
 * 
 * @author youwen
 *
 */
public enum  ImageSize {
	
	SIZE_800("width='800' height='800'",0),
	SIZE_600("width='600' height='600'",9),
	SIZE_350("width='350' height='350'",16),
	SIZE_200("width='200' height='200'",10),
	SIZE_160("width='160' height='160'",11),
	SIZE_130("width='130' height='130'",12),
	SIZE_110("width='110' height='110'",13),
	SIZE_55("width='55' height='55'",14);
	
	private String value;  
    private int index;
    
    private ImageSize(String value,int index){
    	this.value = value;
    	this.index = index;
    }

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
    
    public static ImageSize getImageSize(int imageType){
    	  for (ImageSize size : values()) {
    		  if(size.index==imageType){
    			  return size;
    		  }
    	  }
		return values()[2];
    }

}
