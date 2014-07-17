package com.adapter.utils;

import java.io.File;
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Java2XML {
	String desityDpis[]={"ldpi","mdpi","hdpi","xdpi","xxdpi"};
	float density[]={0.75f,1.0f,1.5f,2.0f,3.0f};
	ArrayList<DpiContent> mDpiContentList = new ArrayList<DpiContent>();
	/**
	 * ��720*1280Ϊ��׼����������
	 */
	final float rate_w=0.5625f;
	
	DpiContent standardDc = new DpiContent(2.0f,"xdpi",720,1280);
    public void BuildXMLDoc() throws IOException, JDOMException {
    	
    	mDpiContentList.add(new DpiContent(0.75f,"ldpi",240,320)); 
    	mDpiContentList.add(new DpiContent(1.0f,"mdpi",320,480)); 
    	mDpiContentList.add(new DpiContent(1.5f,"hdpi",480,800)); 
    	mDpiContentList.add(new DpiContent(1.5f,"hdpi",480,854)); 
    	mDpiContentList.add(new DpiContent(1.5f,"hdpi",540,960)); 
    	mDpiContentList.add(new DpiContent(1.5f,"hdpi",640,960)); 
    	mDpiContentList.add(new DpiContent(2.0f,"xhdpi",640,960)); 
    	mDpiContentList.add(new DpiContent(2.0f,"xhdpi",640,1136)); 
    	mDpiContentList.add(new DpiContent(2.0f,"xhdpi",720,1280)); 
    	mDpiContentList.add(new DpiContent(2.5f,"xhdpi",1080,1800)); 
    	mDpiContentList.add(new DpiContent(2.5f,"xxhdpi",1080,1800)); 
    	mDpiContentList.add(new DpiContent(3.0f,"xxhdpi",1080,1920)); 
    	int length = mDpiContentList.size();
    	for(int j=0;j<length;j++){
    		DpiContent dpiContent = mDpiContentList.get(j);    	
    		
	       // �������ڵ� list;   
            Element root = new Element("resources");   
           // ���ڵ���ӵ��ĵ��У�   
            Document Doc = new Document(root);   
           // �˴� for ѭ�����滻�� ���� ���ݿ��Ľ��������;   
           for (int i = 1; i <= 720; i++) {  
               // �����ڵ� user;   
               Element elements = new Element("dimen");   
               // �� user �ڵ�������� id;   
               elements.setAttribute("name", "w_" + i+"_dip");
               elements.setText(""+getDip(dpiContent,i)+"dip");
               // �����ڵ�list���user�ӽڵ�;  
               root.addContent(elements);  
           }  
            XMLOutputter XMLOut = new XMLOutputter();
            XMLOut.setFormat(Format.getPrettyFormat());
//            new XMLOutputter("", true, "");
//            XMLOutputter outputter = new XMLOutputter("", true, "");
           File file = new File(dpiContent.folderName);
           if(!file.exists()){
        	   file.mkdir();
           }
           // ��� user.xml �ļ���  
            XMLOut.output(Doc, new FileOutputStream(dpiContent.filePath));  
    	}

    } 
    /**
     * �� 720X1280 xhdpi Ϊ��׼���б仯
     * @param dip
     * @param densityDpi
     * @param heighthPx
     * @param widthPx
     * @return
     */
    public float getDip(DpiContent dc,int dip){
    	float resultDip = 0;
    	resultDip = (dc.widthPx*standardDc.density*dip)/(standardDc.widthPx*dc.density);
    	resultDip = ((float)Math.round(resultDip*10))/10;
    	return resultDip;
    }
    
    public static void main(String[] args) {  
       try {  
           Java2XML j2x = new Java2XML();  
           System.out.println("���� mxl �ļ�...");  
           j2x.BuildXMLDoc();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
    }  
    
    public class DpiContent{
    	public float widthDipCount;
    	public float heightDipCount;
    	public float density;
    	public int heighthPx;
    	public int widthPx;
    	public String densityDpi;
    	public String folderName;
    	public String fileName;
    	public String filePath;
    	public DpiContent(float density,String densityDpi,int widthPx,int heighthPx){
    		this.density = density;
    		this.densityDpi = densityDpi;
    		this.widthPx = widthPx;
    		this.heighthPx = heighthPx;
    		widthDipCount = (int) (widthPx/density);
    		heightDipCount = (int) (heighthPx/density);
    		StringBuffer sb = new StringBuffer();
    		sb.append("values-");
    		sb.append(densityDpi);
    		sb.append("-");
    		sb.append(""+heighthPx+"x"+widthPx);
    		folderName = sb.toString();
    		fileName="dimen.xml";
    		filePath=folderName+"/"+fileName;
    	}
    }
}  