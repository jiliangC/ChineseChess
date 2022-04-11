package gxu.data_structure.chess;

/**
 * 表示常量
 */
public interface Constants {
    boolean RED = true;
    boolean BLACK = false;

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

    // 棋子价值系数
    int K_Value = 1;

    // 棋子价值
    int Jiang = 65536;
    int Che = 950;
    int Shi = 200;
    int Xiang = 200;
    int Bing = 130;
    int Ma = 450;
    int Pao = 480;

    // 棋子灵活度系数
    int K_Flexible = 1;
    // 棋子的灵活度 比例系数
    int k_che = 7;
    int k_ma = 13;
    int k_pao = 7;
    int k_xiang = 1;
    int k_shi = 1;
    int k_bing = 15;

    //棋子位置价值的比例系数
    int K_Position = 1;
    //棋子位置价值
    int[][] PositionValues = new int[][]{
            {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            { // 帅
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    50, -40, -50, 0, 0, 0, 0, 0, 0, 0,
                    80, -40, -50, 0, 0, 0, 0, 0, 0, 0,
                    50, -40, -50, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            { // 士
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    40, 0, 30, 0, 0, 0, 0, 0, 0, 0,
                    0, 40, 0, 0, 0, 0, 0, 0, 0, 0,
                    40, 0, 30, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            { // 相
                    0, 0, 10, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    30, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 35, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    30, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 10, 0, 0, 0, 0, 0, 0, 0
            },
            { // 马
                    20, 20, 80, 45, 80, 90, 105, 120, 80, 80,
                    -20, 45, 45, 90, 120, 135, 175, 125, 115, 105,
                    20, 80, 105, 105, 135, 125, 145, 135, 200, 135,
                    20, 80, 105, 90, 125, 145, 175, 145, 135, 120,
                    20, -10, 80, 110, 120, 175, 150, 175, 105, 80,
                    20, 80, 105, 90, 125, 145, 175, 145, 135, 120,
                    20, 80, 105, 105, 135, 125, 145, 135, 200, 135,
                    -20, 45, 45, 90, 120, 135, 175, 125, 115, 105,
                    20, 20, 80, 45, 80, 90, 105, 120, 80, 80
            },
            { // 车
                    20, 115, 100, 110, 155, 180, 180, 180, 185, 185,
                    120, 95, 115, 148, 185, 190, 200, 198, 203, 195,
                    105, 100, 105, 135, 172, 180, 195, 190, 198, 190,
                    140, 155, 140, 185, 215, 205, 220, 215, 230, 210,
                    115, 115, 135, 190, 215, 225, 230, 225, 245, 220,
                    140, 155, 140, 185, 215, 205, 220, 215, 230, 210,
                    105, 100, 105, 135, 172, 180, 195, 190, 198, 190,
                    120, 95, 115, 148, 185, 190, 200, 198, 203, 195,
                    20, 115, 100, 110, 155, 180, 180, 180, 185, 185
            },
            { // 炮
                    97, 97, 98, 97, 96, 97, 97, 98, 100, 102,
                    97, 98, 97, 97, 97, 97, 100, 98, 100, 102,
                    97, 100, 102, 97, 100, 97, 100, 96, 96, 98,
                    98, 100, 101, 97, 97, 97, 99, 92, 93, 92,
                    100, 100, 104, 97, 102, 102, 102, 93, 89, 90,
                    100, 100, 101, 97, 97, 97, 99, 92, 93, 92,
                    98, 100, 102, 97, 100, 97, 100, 96, 96, 98,
                    97, 98, 97, 97, 97, 97, 100, 98, 100, 102,
                    97, 97, 98, 97, 96, 97, 97, 98, 100, 102
            },
            { // 兵
                    0, 0, 0, -1, 3, 8, 13, 15, 18, 1,
                    0, 0, 0, 0, 0, 17, 22, 28, 36, 3,
                    0, 0, 0, -3, 7, 18, 30, 42, 56, 9,
                    0, 0, 0, 0, 0, 21, 42, 73, 95, 10,
                    0, 0, 0, 3, 8, 26, 52, 80, 118, 12,
                    0, 0, 0, 0, 0, 21, 42, 73, 95, 10,
                    0, 0, 0, -3, 7, 18, 30, 42, 56, 9,
                    0, 0, 0, 0, 0, 17, 22, 28, 36, 3,
                    0, 0, 0, -1, 3, 8, 13, 15, 18, 1
            }
    };
}
