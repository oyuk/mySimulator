import java.util.ArrayList;

/**
 * Created by oky on 14/11/27.
 */
public class PagingArea {

    ArrayList<Integer> pa_x;
    ArrayList<Integer> pa_y;
    int pa_move_count;//PAを移動した回数


    int location_legistration_model_num;

    /*目的地方向
    * 移動モデルが2,3の場合に使用
    * */
    int dest_direction;


    public PagingArea(int location_legistration_model_num,int dest_direction) {
        pa_x = new ArrayList<Integer>();
        pa_y = new ArrayList<Integer>();
        pa_move_count = 0;

        this.dest_direction = dest_direction;
        this.location_legistration_model_num = location_legistration_model_num;
    }

    /*ページングエリアの設定。

        * */
    public void set_PA(int x,int y){

//        現在の座標の周辺を追加
        if (location_legistration_model_num <= 1) {
            pa_x.add(x - 1);
            pa_x.add(x);
            pa_x.add(x + 1);
            pa_x.add(x - 1);
            pa_x.add(x);
            pa_x.add(x + 1);
            pa_x.add(x - 1);
            pa_x.add(x);
            pa_x.add(x + 1);

            pa_y.add(y - 1);
            pa_y.add(y - 1);
            pa_y.add(y - 1);
            pa_y.add(y);
            pa_y.add(y);
            pa_y.add(y);
            pa_y.add(y + 1);
            pa_y.add(y + 1);
            pa_y.add(y + 1);

        }else {

            //目的地方向に広くPAをとる
            switch (dest_direction) {
                case 0://上
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);


                    pa_y.add(y);
                    pa_y.add(y);
                    pa_y.add(y);
                    pa_y.add(y + 1);
                    pa_y.add(y + 1);
                    pa_y.add(y + 1);
                    pa_y.add(y + 2);
                    pa_y.add(y + 2);
                    pa_y.add(y + 2);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x - 1);
                        pa_x.add(x);
                        pa_x.add(x + 1);
                        pa_y.add(y + 3 + i);
                        pa_y.add(y + 3 + i);
                        pa_y.add(y + 3 + i);
                    }

                    break;
                case 1://右上
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 2);
                    pa_x.add(x + 2);

                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y + 1);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y + 2);
                    pa_y.add(y + 2);
                    pa_y.add(y + 1);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x + 2 + i);
                        pa_x.add(x + 3 + i);
                        pa_x.add(x + 3 + i);
                        pa_y.add(y + 3 + i);
                        pa_y.add(y + 3 + i);
                        pa_y.add(y + 2 + i);
                    }

                    break;
                case 2://右
                    pa_x.add(x);
                    pa_x.add(x);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 2);
                    pa_x.add(x + 2);
                    pa_x.add(x + 2);

                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x + 3 + i);
                        pa_x.add(x + 3 + i);
                        pa_x.add(x + 3 + i);
                        pa_y.add(y + 1);
                        pa_y.add(y);
                        pa_y.add(y - 1);
                    }

                    break;
                case 3://右下
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 1);
                    pa_x.add(x + 2);
                    pa_x.add(x + 2);

                    pa_y.add(y - 1);
                    pa_y.add(y);
                    pa_y.add(y + 1);
                    pa_y.add(y - 1);
                    pa_y.add(y - 1);
                    pa_y.add(y);
                    pa_y.add(y - 2);
                    pa_y.add(y - 2);
                    pa_y.add(y - 1);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x + 2 + i);
                        pa_x.add(x + 3 + i);
                        pa_x.add(x + 3 + i);
                        pa_y.add(y - 3 - i);
                        pa_y.add(y - 3 - i);
                        pa_y.add(y - 2 - i);
                    }

                    break;
                case 4://下
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);

                    pa_y.add(y);
                    pa_y.add(y);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y - 1);
                    pa_y.add(y - 1);
                    pa_y.add(y - 2);
                    pa_y.add(y - 2);
                    pa_y.add(y - 2);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x - 1);
                        pa_x.add(x);
                        pa_x.add(x + 1);
                        pa_y.add(y - 3 - i);
                        pa_y.add(y - 3 - i);
                        pa_y.add(y - 3 - i);
                    }

                    break;
                case 5://左下
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x - 2);
                    pa_x.add(x - 2);
                    pa_x.add(x - 1);

                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y - 1);
                    pa_y.add(y - 1);
                    pa_y.add(y - 2);
                    pa_y.add(y - 2);

                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 2 - i);
                        pa_y.add(y - 2 - i);
                        pa_y.add(y - 3 - i);
                        pa_y.add(y - 3 - i);
                    }

                    break;
                case 6://左
                    pa_x.add(x);
                    pa_x.add(x);
                    pa_x.add(x);
                    pa_x.add(x - 1);
                    pa_x.add(x - 1);
                    pa_x.add(x - 1);
                    pa_x.add(x - 2);
                    pa_x.add(x - 2);
                    pa_x.add(x - 2);

                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y - 1);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 3 - i);
                        pa_y.add(y + 1);
                        pa_y.add(y);
                        pa_y.add(y - 1);
                    }

                    break;
                case 7://左上
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x + 1);
                    pa_x.add(x - 1);
                    pa_x.add(x - 1);
                    pa_x.add(x);
                    pa_x.add(x - 2);
                    pa_x.add(x - 2);
                    pa_x.add(x - 1);

                    pa_y.add(y - 1);
                    pa_y.add(y);
                    pa_y.add(y + 1);
                    pa_y.add(y);
                    pa_y.add(y + 1);
                    pa_y.add(y + 1);
                    pa_y.add(y + 1);
                    pa_y.add(y + 2);
                    pa_y.add(y + 2);


                    for (int i = 0; i < Main.location_registration_area_extent; i++) {
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 3 - i);
                        pa_x.add(x - 2 - i);
                        pa_y.add(y + 2 + i);
                        pa_y.add(y + 3 + i);
                        pa_y.add(y + 3 + i);
                    }

                    break;
                default:
                    break;
            }

        }


    }


    public void paging(int x,int y){

        int[] tmp = mn_exist_row_column(x, y);
        int mn_exist_row = tmp[0];
        int mn_exist_column = tmp[1];


        if (location_legistration_model_num <= 1){

            if (mn_exist_column == 1 && mn_exist_row == 1){
                Main.paging_cell_num += Main.C_paging_33[0];
                Main.paging_delay++;
            }else{
                Main.paging_cell_num += Main.C_paging_33[1];
                Main.paging_delay += 2;
            }

        }else {


        /*
        * PAの広さ
        * 0:3*3
        * 1:3*4
        * 2:3*5
        *
        * */

            switch (Main.location_registration_area_extent) {
                case 0:

                    if (mn_exist_row == 1) {
                        Main.paging_cell_num += Main.C_proposed_paging_33[0];
                        Main.paging_delay++;
                    } else {
                        Main.paging_cell_num += Main.C_proposed_paging_33[1];
                        Main.paging_delay += 2;
                    }

                    break;
                case 1:

                    if (mn_exist_row == 1 || mn_exist_row == 2) {
                        Main.paging_cell_num += Main.C_proposed_paging_34[0];
                        Main.paging_delay++;
                    } else {
                        Main.paging_cell_num += Main.C_proposed_paging_34[1];
                        Main.paging_delay += 2;
                    }

                    break;
                case 2:

                    if (mn_exist_row == 2) {
                        Main.paging_cell_num += Main.C_proposed_paging_35[0];
                        Main.paging_delay++;
                    } else if (mn_exist_row == 1 || mn_exist_row == 3) {
                        Main.paging_cell_num += Main.C_proposed_paging_35[1];
                        Main.paging_delay += 2;
                    } else {
                        Main.paging_cell_num += Main.C_proposed_paging_35[2];
                        Main.paging_delay += 3;
                    }

                    break;
                default:
                    break;
            }

        }

        Main.paging_num++;
    }


    //mnがページングエリア内にいるか判定
    public boolean check(int x,int y){

        boolean mn_in = false;

        for (int i = 0; i < pa_x.size(); i++) {

            if(pa_x.get(i) == x && pa_y.get(i) == y){
                mn_in = true;
                break;
            }
        }

        return mn_in;
    }


    public int mn_exist_num(int x,int y){

        int row = 0;
        int i;

        for (i = 0; i < pa_x.size(); i++) {
            if(pa_x.get(i) == x && pa_y.get(i) == y){
                break;
            }
        }

        return i;
    }


    public int[] mn_exist_row_column(int x, int y){

        int row = 0;
        int column = 0;

        for (int i = 0; i < pa_x.size(); i++) {

            if(pa_x.get(i) == x && pa_y.get(i) == y){

                row = i / 3;
                column = i % 3;

//                System.out.println("i :"+i);
//                System.out.println("row :"+row);
//                System.out.println("column :"+column);

                break;
            }
        }

        int[] retval = {row,column};


        return retval;
    }


}
