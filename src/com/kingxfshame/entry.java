package com.kingxfshame;

import com.mysql.cj.x.protobuf.MysqlxExpect;

import java.awt.*;
import java.awt.event.KeyEvent;

public class entry {
    public int x,y;
    public int width,height;
    public int textOffsetX,textOffsety ;
    public String text;
    public String font;
    public int fontSize;
    public int fontStyle;
    public boolean isActive;
    public entry(){
        this.font = "TimesRoman";
        this.fontSize = 12;
        this.fontStyle = Font.PLAIN | Font.ITALIC;

        this.width = 100;
        this.height = 25;
        this.text = "";
        this.textOffsetX = 60;
        this.textOffsety = 60;
        this.isActive = false;
    }
    public void update(Graphics g){
        if(!isActive)return;
        g.setColor(new Color(255,0,0));
        g.drawRect(x,y,width,height);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font(font,fontStyle,fontSize));
        g.drawString(text,x+ textOffsetX,y + textOffsety);
    }
    public void keyPress(KeyEvent e){
        if(!isActive)return;
        text += e.getKeyChar();
        try{
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) text = text.substring(0,text.length() -1);
        else text+= e.getKeyChar();
        }
        catch(Exception ex){

        }
    }
}
