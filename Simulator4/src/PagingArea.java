import java.util.ArrayList;

/**
 * Created by oky on 2014/01/02.
 */
public class PagingArea {

    int area_extent;//速度ごとのページングエリアの広さを決める値
    ArrayList<Integer> pa_x;
    ArrayList<Integer> pa_y;
    int pa_move_count;//PAを移動した回数
    int pa_size;


    public PagingArea() {
        pa_x = new ArrayList<Integer>();
        pa_y = new ArrayList<Integer>();
        pa_move_count = 0;
        pa_size = 0;
    }

    /*ページングエリアの設定。

        * */
    public void set_PA(int direction,int x,int y,int stay_time){

        set_area_extent(stay_time);

        System.out.println("       setPA:"+direction);

        switch (direction){
            case 0://上
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);

                pa_y.add(y-1);pa_y.add(y-1);pa_y.add(y-1);
                pa_y.add(y);pa_y.add(y);pa_y.add(y);
                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y+1);

                for (int i = 0; i < area_extent; i++) {
                    pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                    pa_y.add(y+2+i);pa_y.add(y+2+i);pa_y.add(y+2+i);
                }

                break;
            case 1://右上
                pa_x.add(x-1);pa_x.add(x-1);pa_x.add(x);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x);pa_x.add(x+1);pa_x.add(x+1);

                pa_y.add(y);pa_y.add(y-1);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y);

                for (int i = 0; i < area_extent; i++) {
                    pa_x.add(x+2+i);pa_x.add(x+2+i);pa_x.add(x+1+i);
                    pa_y.add(y+1+i);pa_y.add(y+2+i);pa_y.add(y+2+i);
                }
                break;
            case 2://右
                pa_x.add(x-1);pa_x.add(x-1);pa_x.add(x-1);
                pa_x.add(x);pa_x.add(x);pa_x.add(x);
                pa_x.add(x+1);pa_x.add(x+1);pa_x.add(x+1);

                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);

                for (int i = 0; i < area_extent; i++) {
                pa_x.add(x+2+i);pa_x.add(x+2+i);pa_x.add(x+2+i);
                pa_y.add(y-1);pa_y.add(y);pa_y.add(y+1);
            }

                break;
            case 3://右下
                pa_x.add(x);pa_x.add(x-1);pa_x.add(x-1);
                pa_x.add(x+1);pa_x.add(x);pa_x.add(x-1);
                pa_x.add(x+1);pa_x.add(x+1);pa_x.add(x);

                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y);pa_y.add(y-1);pa_y.add(y-1);

                for (int i = 0; i < area_extent; i++) {
                    pa_x.add(x+2+i);pa_x.add(x+2+i);pa_x.add(x+1+i);
                    pa_y.add(y-1-i);pa_y.add(y-2-i);pa_y.add(y-2-i);
                }
                break;
            case 4://下
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);

                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y+1);
                pa_y.add(y);pa_y.add(y);pa_y.add(y);
                pa_y.add(y-1);pa_y.add(y-1);pa_y.add(y-1);

                for (int i = 0; i < area_extent; i++) {
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_y.add(y-2-i);pa_y.add(y-2-i);pa_y.add(y-2-i);
            }

                break;
            case 5://左下
                pa_x.add(x);pa_x.add(x+1);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x);pa_x.add(x+1);
                pa_x.add(x-1);pa_x.add(x-1);pa_x.add(x);

                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y);pa_y.add(y-1);pa_y.add(y-1);

                for (int i = 0; i < area_extent; i++) {
                    pa_x.add(x-2-i);pa_x.add(x-2-i);pa_x.add(x-1-i);
                    pa_y.add(y-1-i);pa_y.add(y-2-i);pa_y.add(y-2-i);
                }
                    break;
            case 6://左
                pa_x.add(x+1);pa_x.add(x+1);pa_x.add(x+1);
                pa_x.add(x);pa_x.add(x);pa_x.add(x);
                pa_x.add(x-1);pa_x.add(x-1);pa_x.add(x-1);

                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);

                for (int i = 0; i < area_extent; i++) {
                pa_x.add(x-2-i);pa_x.add(x-2-i);pa_x.add(x-2-i);
                pa_y.add(y-1);pa_y.add(y);pa_y.add(y+1);
            }
                break;
            case 7://左上
                pa_x.add(x+1);pa_x.add(x+1);pa_x.add(x);
                pa_x.add(x+1);pa_x.add(x);pa_x.add(x-1);
                pa_x.add(x);pa_x.add(x-1);pa_x.add(x-1);

                pa_y.add(y);pa_y.add(y-1);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y);pa_y.add(y-1);
                pa_y.add(y+1);pa_y.add(y+1);pa_y.add(y);

                for (int i = 0; i < area_extent; i++) {
                    pa_x.add(x-2-i);pa_x.add(x-2-i);pa_x.add(x-1-i);
                    pa_y.add(y+1+i);pa_y.add(y+2+i);pa_y.add(y+2+i);
                }
                break;
            default:
                break;
        }

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

                row = i / 3;
               // System.out.println(" mn_exist row:"+row+" i:"+i+" ("+x+","+y+")");
                break;
            }
        }

        return i;

    }

    public int mn_exist_row(int x,int y){

        int row = 0;
        for (int i = 0; i < pa_x.size(); i++) {

            if(pa_x.get(i) == x && pa_y.get(i) == y){

                row = i / 3;
                System.out.println(" mn_exist row:"+row+" i:"+i);
                break;
            }
        }

        return row;

    }

    public void set_area_extent(int stay_time){

        if(stay_time == Main.mn_mag_stay_time[0]){
            area_extent = 0;
        }else if(stay_time <= Main.mn_mag_stay_time[1] && stay_time >= Main.mn_mag_stay_time[3]){
            area_extent = 0;
        }else if(stay_time <= Main.mn_mag_stay_time[4] && stay_time >= Main.mn_mag_stay_time[7]){
            area_extent = 2;
        }else{
            area_extent = 4;
        }

        pa_size = 3 + area_extent;

    }

}
