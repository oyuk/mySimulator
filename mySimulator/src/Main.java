/**
 * Created by oky on 14/11/26.
 */
public class Main {

    //フィールドの範囲
    static final int fieldx = 200;
    static final int fieldy = 200;

    //シミュレーション時間
    static final int SimuTime = 60*60*1;
    static final int refresh_rate = 1;
    //MNの数
    static final int nodeNum = 1;

    //MNがMAGにいる時間
    static final int mn_bs_stay_time[] = {102,154};

//    MNがactive状態になっている時間
    static final int active_timer[] = {30,60};


    /*PagingManager
    *
    * lambda:ポアソン過程のλ
    * per_unit_time:単位時間
    *
    */
    final static int lambda = 3;
    //1時間を単位時間とする
    final static int per_unit_time = 60*60;


    /*テキストファイルから読み込むパラメータ
    *
    * ノード数(ランダム、二地点最短、二地点迂回)
    * 基地局通信半径
    *
    *
    *
    *
    *
    *
    * */

    /*
    * 移動端末のパラメータ
    *
    * ランダム
    * 位置登録エリアは3*3
    *
    *
    * 二地点
    *
    * 最短
    * 目的地
    *
    * 迂回
    * 目的地
    * 進行方向
    *
    * */



    public static void main(String[] args) {
        new Simulator().startSimulation();
    }
}
