package gxu.data_structure.chess;

/**
 * 表示常量
 */
public interface Constants {
    int EMPTY = 0;
    int select = 1;

    int redJiang = 8;
    int redShi = 9;
    int redXiang = 10;
    int redMa = 11;
    int redChe = 12;
    int redPao = 13;
    int redZu = 14;

    int blackJiang = 16;
    int blackShi = 17;
    int blackXiang = 18;
    int blackMa = 19;
    int blackChe = 20;
    int blackPao = 21;
    int blackZu = 22;

    int lineHeight = 57;//每两个棋子点的间隔是57个像素.
    int x0 = 51, y0 = 53;//第一个点的坐标
    int chessSize = 55; //棋子图片的大小是55像素

    //楚河汉界的两个y值
    int riverYb = 4;
    int riverYR = 5;

    // 棋子价值
    int Jiang = 100000;
    int Che = 90;
    int Shi = 20;
    int Xiang = 20;

    // 兵
    int crossBing = 20;
    int uncrossBing = 10;
    int underBing = 10;
    int inNineBing = 25;

    // 马
    int Ma = 40;
    int inNineMa = 30;
    int boundaryMa = 35;
    int cornerMa = 30;

    // 炮
    int Pao = 45;
    int centerPao = 50;
    int underPao = 50;

}
