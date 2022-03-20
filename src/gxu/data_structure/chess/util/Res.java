package gxu.data_structure.chess.util;

import java.awt.image.BufferedImage;

public interface Res {

    //声音资源:
    MP3Player jjPlayer = new MP3Player(Resource.getStream("ding.mp3"));

    //棋子资源
    BufferedImage imageChessBoard = Resource.getImage("main.gif");
    BufferedImage redCheImg = Resource.getImage("红车.GIF");
    BufferedImage redJiangImg = Resource.getImage("红将.gif");
    BufferedImage redMaImg = Resource.getImage("红马.gif");
    BufferedImage redPaoImg = Resource.getImage("红炮.gif");
    BufferedImage redShiImg = Resource.getImage("红士.gif");
    BufferedImage redXiangImg = Resource.getImage("红象.gif");
    BufferedImage redZuImg = Resource.getImage("红卒.gif");
    BufferedImage blackCheImg = Resource.getImage("黑车.gif");
    BufferedImage blackJiangImg = Resource.getImage("黑将.gif");
    BufferedImage blackMaImg = Resource.getImage("黑马.gif");
    BufferedImage blackPaoImg = Resource.getImage("黑炮.gif");
    BufferedImage blackShiImg = Resource.getImage("黑士.gif");
    BufferedImage blackXiangImg = Resource.getImage("黑象.gif");
    BufferedImage blackZuImg = Resource.getImage("黑卒.gif");


    //点击图片
    BufferedImage selectedImg = Resource.getImage("select.gif");
    BufferedImage cursorImg = Resource.getImage("光标.gif");
}
