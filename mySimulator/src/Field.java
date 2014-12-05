/**
 * Created by oky on 14/11/27.
 */
public class Field {

    //シングルトンクラス
    private static Field instance = new Field();

    private Field() {
    }

    public static Field getInstance() {
        return instance;
    }

    private BS bs[][];

    void initField() {

        bs = new BS[Main.fieldx][Main.fieldy];

        for (int i = 0; i < Main.fieldx; i++) {

            for (int j = 0; j < Main.fieldy; j++) {

                bs[i][j] = new BS(i, j);
                
            }
        }
    }


    //MNをBSへ登録
    void reg_bs(int x, int y,boolean send_pbu,boolean active) {
        bs[x][y].reg_mn(send_pbu,active);
    }

    //BSからMNを削除
    void del_bs(int x, int y) {
        bs[x][y].del_mn();
    }

    //シミュレーション開始時の登録
    void initial_mn_reg(int x, int y) {
        bs[x][y].first_reg_mn();
    }

    void print() {
        for (int i = 0; i < Main.fieldx; i++) {
            for (int j = 0; j < Main.fieldy; j++) {

                if (bs[i][j].cost > 0) bs[i][j].state();
//				bs[i][j].state();
            }
        }
    }


}
