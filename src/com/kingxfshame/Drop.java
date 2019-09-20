package com.kingxfshame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Drop {
    public int dropheight,dropwidth,flDropPos_left,flGameWindow;
    public float droptop,dropleft,dropspeed,dropright,dropbotton;
    public Image dropimage;
    public float flDelta_time;



    public Drop(int _width,int _height, float _top,float _left, float _speed) throws IOException {
        this.dropwidth = _width;
        this.dropheight = _height;
        this.droptop= _top;
        this.dropleft = _left;
        this.dropspeed= _speed;
        dropimage = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"))
                .getScaledInstance(dropheight,dropwidth,Image.SCALE_DEFAULT);

    }
    public void dropRespawn(float _right,float _botton){
        this.dropright = _right;
        this.dropbotton = _botton;
    }

}
