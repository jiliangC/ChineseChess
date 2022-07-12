package gxu.data_structure.chess.util;

import java.awt.image.BufferedImage;

public interface Res {

    //声音资源:
    MP3Player click_mp3 = new MP3Player(Resource.getStream("audio_click.mp3"));
    MP3Player select_mp3 = new MP3Player(Resource.getStream("audio_select.mp3"));
    MP3Player eat_mp3 = new MP3Player(Resource.getStream("audio_eat.mp3"));
    MP3Player win_mp3 = new MP3Player(Resource.getStream("audio_win.mp3"));
    MP3Player defeat_mp3 = new MP3Player(Resource.getStream("audio_defeat.mp3"));
    MP3Player jiang_mp3 = new MP3Player(Resource.getStream("audio_jiang.mp3"));

    //声音类型：
    int t_select = 1;
    int t_click = 2;
    int t_win = 3;
    int t_defeat = 4;
    int t_jiang = 5;
    int t_eat = 6;


    //棋子资源
    BufferedImage imageChessBoard = Resource.getImage("main.gif");
    BufferedImage redCheImg = Resource.getImage("红车.gif");
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
    BufferedImage shootimage = Resource.getImage("shoot.png");


    //点击图片
    BufferedImage selectedImg = Resource.getImage("select.gif");
    BufferedImage cursorImg = Resource.getImage("光标.gif");
}
