import mapper.MydictMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import pojo.Mydict;
import pojo.User;
import utils.MyBatisUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyTest {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode removeElements(ListNode head, int val) {
        ListNode preHead = new ListNode(0,head);

        if(head==null||head.next==null)return null;

        ListNode cur = preHead;

        while(cur.next!=null){
            if(cur.next.val==val){
                cur.next = cur.next.next;
            }
            if (cur.next!=null){
                cur = cur.next;
            }

        }

        return preHead.next;

    }

    @Test
    public void test1(){
//        1,2,6,3,4,5,6
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(6);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = new ListNode(6);

        removeElements(head,6);

    }

    @Test
    public void test2(){
        int i = divisorSubstrings(430043, 2);
        System.out.println(i);
    }

    public int divisorSubstrings(int num, int k) {
        String numStr  = num + "";
        int count = 0;
        int length = numStr.length();
        for(int i=0;i<=length-k;i++){
            int mid = Integer.parseInt(numStr.substring(i,i+k));
            if(mid!=0&&num%mid==0)count++;
        }
        return count;
    }

    @Test
    public void testLc2(){
        int[] nums = new int[]{2,3,1,0};
        System.out.println(waysToSplitArray(nums));
    }
    public int waysToSplitArray(int[] nums) {
        int count = 0;
        int length = nums.length;
        //i处算左边的
        for(int i=0;i<length-1;i++){
            int left = 0;
            int right = 0;
            for(int j=0;j<=i;j++){
                left+=nums[j];
            }
            for (int j=i+1;j<length;j++){
                right+=nums[j];
            }
            if(left>=right)count++;
        }
        return count;
    }

//[1,5],[10,11],[12,18],[20,25],[30,32]

    @Test
    public void testLc3(){
        int[][] nums = new int[][]{{1,5},{10,11},{12,18},{20,25},{30,32}};
    }

//    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
//        int max = 0;
//        Arrays.sort(tiles, (o1, o2) -> Integer.compare(o1[0], o2[0]));
//        int length = tiles.length;
//        if(length==1)return (tiles[0][1]-tiles[0][0]+1);
//
//        int cover = 0;
//        for(int i=0;i<length;i++){
//            if(carpetLen<=(tiles[i][1]-tiles[i][0]+1)){
//                cover = carpetLen;
//                max = Math.max(cover,max);
//            }else {
//                for(int j=i+1;j<length;j++){
//                    int lenLeft = tiles[j][0]-tiles[i][0]+1;
//                    int lenRight = tiles[j][1] - tiles[i][0]+1;
//                    if(carpetLen<lenLeft){
//                        max = Math.max(max,(tiles[i][1]-tiles[i][0]+1));
//                        break;}
////                    }else if(){
////
////                    }
//                }
//            }
//        }
//
//    }

    @Test
    public void testLc4(){
        String s = "abcde";
        System.out.println(largestVariance(s));
    }

    public int largestVariance(String s) {
        int length  = s.length();
        int max = 0;
        for(int i=1;i<length;i++){//i代表子字符串的长度+1
            for(int j=0;j<length-i;j++){
                Map<Character,Integer> map = new HashMap<>();

                String mid = s.substring(j,j+i+1);
                int l2 = i+1;
                for(int k=0;k<l2;k++){
                    int min2 = Integer.MAX_VALUE;
                    int max2 = 0;
                    char c = mid.charAt(k);
                    if(!map.containsKey(c)){
                        map.put(c,1);
                    }else {
                        map.put(c,map.get(c)+1);
                    }

                    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                        min2 = Math.min(min2,entry.getValue());
                        max2 = Math.max(max2,entry.getValue());
                    }

                    max = Math.max(max2-min2,max);

                }



            }
        }
        return max;
    }




}
