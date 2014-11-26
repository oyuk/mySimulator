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

    MAG mag[][];

    void initField() {

        mag = new MAG[Main.fieldx][Main.fieldy];


        for (int i = 0; i < Main.fieldx; i++) {

            for (int j = 0; j < Main.fieldy; j++) {

                mag[i][j] = new MAG(i, j);

            }


        }
    }

    //MNをMAGへ登録
    void reg_mag(int x, int y, int id,boolean send_pbu,boolean active) {
        mag[x][y].reg_mn(id,send_pbu,active);
    }

    //MAGからMNを削除
    void del_mag(int x, int y, int id) {
        mag[x][y].del_mn(id);
    }

    //シミュレーション開始時の登録
    void initial_mn_reg(int x, int y, int id) {
        mag[x][y].first_reg_mn(id);
    }

    void print() {
        for (int i = 0; i < Main.fieldx; i++) {
            for (int j = 0; j < Main.fieldy; j++) {

                if (mag[i][j].cost > 0) mag[i][j].state();
//				mag[i][j].state();
            }
        }
    }


}
