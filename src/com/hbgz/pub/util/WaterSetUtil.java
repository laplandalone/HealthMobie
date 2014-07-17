package com.hbgz.pub.util;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

class PicSize
{
	private int height;
	private int width;
	
	public int getHeight() 
	{
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}

	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public PicSize()
	{
		
	}
	
	public PicSize(int height,int width)
	{
		this.height = height;
		this.width = width;
	}
}


public class WaterSetUtil
{
	private static PicSize[] picSize = {new PicSize(1189,1682),new PicSize(841,1189),new PicSize(594,841),new PicSize(420,594),new PicSize(297,420),new PicSize(210,297),new PicSize(148,210),new PicSize(105,148),new PicSize(74,105),new PicSize(52,74),new PicSize(37,52),new PicSize(26,37)};
	
	private static int fontSize[] = {24,22,20,18,15,12,10,7,7,7,6,5};
	
	//获得合适的字号
	private static int getApproFontSize(int height,int width)
	{
		int temp,pos,count;
		for(pos = 0,count = picSize.length;pos < count;pos++)
		{
			temp = picSize[pos].getWidth();
			if(temp <= width)
			{
				break;
			}
		}
		if(pos>=count)
		{
			return fontSize[pos-1];
		}
		else if(pos == 0)
		{
			return fontSize[0];
		}
		else
		{
			if(Math.abs(height - picSize[pos-1].getHeight()) > Math.abs(height - picSize[pos].getHeight()))
			{
				return fontSize[pos];
			}
			else
			{
				return fontSize[pos-1];
			}
		}
	}
	
	private static int ptToPsConverter(int size)
	{
		double result = size * (1.0/72)*96;
		return (int)Math.round(result);
	}
	
	/**
	 * @author : tiankang
	 * @param src 源地址
	 * @param dest 目的地址
	 * @param flag 1:代表文字水印 2:代表图片水印
	 * @param param[0] 当flag为1时，该值为对应的水印文字;当flag为2时,该值为对应的水印链接 ; param[1] 为角度值 ;
	 */
	public static void addWaterMark(File src,File dest,int flag,Object... param) throws Exception
	{
		Image img = ImageIO.read(src);
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		BufferedImage bufImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufImg.createGraphics();
		//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img, 0, 0, null);
		switch(flag)
		{
			case 1:
				g.setColor(new Color(120,120,120));
				g.setBackground(Color.black);
				g.drawString(String.valueOf(param[0]), (int)(width/1.2), (int)(height/1.1));
				break;
			case 2:
				Image watImg = ImageIO.read((File)param[0]);
				int watWidth = watImg.getWidth(null);
				int watHeight = watImg.getHeight(null);
				PicSize picSize = getWatSize(height , width , watHeight , watWidth);
				g.drawImage(watImg, (int)(width/1.5), (int)(height/1.5), picSize.getWidth() , picSize.getHeight() , null);
				break;
			case 3:
				int fSize = getApproFontSize(height, width);
				int hunite = ptToPsConverter(fSize);
				int wunite = (int)(hunite/1.2);
				g.setColor(Color.RED);
				g.setFont(new Font("Default",Font.BOLD,fSize));
				if(param[1] != null)
				{
					double degree = Double.parseDouble(String.valueOf(param[1]));
					g.rotate(Math.toRadians(degree), (double)width/2, (double)height/2);
				}
				int basHeight = 2 * hunite;
				int basWidth = 9 * wunite;
				int offsetX = (int)(wunite/20);
				int offsetY = (int)(hunite/4);
				int widthVal = 6;
				g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
				
				g.drawLine(width - basWidth - widthVal * wunite , height - basHeight - hunite , width - widthVal * wunite , height - basHeight - hunite );
				g.drawLine(width - basWidth - widthVal * wunite , height - basHeight - hunite , width - basWidth - widthVal * wunite , height - hunite );
				g.drawLine(width - basWidth - widthVal * wunite , height - hunite , width - widthVal * wunite , height - hunite );
				g.drawLine(width - widthVal * wunite , height - basHeight - hunite , width - widthVal * wunite , height - hunite );

				g.drawString(new String(String.valueOf(param[0]).substring(0,10).getBytes(),"gbk"), width - basWidth - widthVal * wunite + offsetX  , height - 2 * hunite - offsetY);
				g.drawString(new String(String.valueOf(param[0]).substring(10).getBytes(),"gbk"), width - basWidth - widthVal * wunite + offsetX , height - hunite - offsetY);
				break;
			default:
				break;
		}
		g.dispose();
		OutputStream os = new FileOutputStream(dest);
		ImageIO.write(bufImg, "JPG", os);
		os.close();
	}
	
	private static PicSize getWatSize(int sourceHeight , int sourceWidth , int watHeight , int watWidth)
	{
		PicSize picSize = new PicSize();
		int tempHeight = sourceHeight - (int)(sourceHeight / 1.5);
		int tempWidth = sourceWidth - (int)(sourceWidth / 1.5);
		int mulHeight = watHeight / tempHeight;
		int mulWidth = watWidth / tempWidth ;
		if(mulHeight == 0)
		{
			picSize.setHeight(watHeight - 4);
		}
		else
		{
			picSize.setHeight(tempHeight - 4);
		}
		if(mulWidth == 0)
		{
			picSize.setWidth(watWidth - 4);
		}
		else
		{
			picSize.setWidth(tempWidth - 4);
		}
		return picSize;
	}
}
