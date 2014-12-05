import java.io.*;

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
    static final int active_timer[] = {15,30,60};


    /*PagingManager
    *
    * lambda:ポアソン過程のλ
    * per_unit_time:単位時間
    *
    */
    final static int lambda = 3;
    //1時間を単位時間とする
    final static int per_unit_time = 60*60;


    final static int[] C_paging_33 = {1,9};

    final static int[] C_proposed_paging_33 = {3,9};
    final static int[] C_proposed_paging_34 = {6,12};
    final static int[] C_proposed_paging_35 = {3,9,15};

    static int paging_area_extent;


/*位置登録をした回数*/
    static int location_registration_num = 0;

    /*ページングエリアを移動した回数*/
    static int pagingarea_move_num = 0;

    /*平均ページングディレイ*/
    static int paging_delay = 0;

    /*ページングしたセル数*/
    static int paging_cell_num = 0;

    /*ページングした回数*/
    static int paging_num = 0;

    /*ページングコスト * ページング回数*/
    static int total_paging_cost = 0;

    /*移動した回数*/
    static int move_count = 0;


    /*テキストファイルから読み込むパラメータ
    *
    * ノード数(ランダム、二地点最短、二地点迂回)
    * 基地局通信半径
    * 位置登録エリアの大きさ
    *
    *
    *
    *
    *
    * */


    /*
    *
    * 入力するテキストファイルの書式
    *
    * シミュレーション時間 0.5,1,1.5,2
    *
    *           30分、１時間、１時間３０分、二時間
    *
    * 基地局通信半径 1,1.5
    *
    *            1km,1.5km
    *
    * 位置登録エリアの大きさ 0,1,2
    *
    *             3*3,3*4,3*5
    *
    * 移動端末の移動パターン 1,2,3
    *
    *             移動モデルの番号
    *             1:ランダム
    *             2:二地点最短
    *             3:二地点迂回
    *
    *
    *
    *
    * */

    static final String filePath = "/Users/oky/Dropbox/research/seminar/M1/ronbun/simulator/test.txt";

    public static void main(String[] args) {

        readFile();
//        new Simulator().startSimulation();
    }


    private static void readFile(){

        try {

            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }

            br.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }


}
