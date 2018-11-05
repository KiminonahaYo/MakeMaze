package test01;

//入力とエラーの種類を読み込み
import java.util.InputMismatchException;
import java.util.Scanner;

//穴掘り法迷路のクラスを読み込み
import maze_anahori_2D.Maze2D_Anahori_Make;

//メイン関数クラス
public class MainFunction
{
	//メイン関数
	public static void main(String[] args)
	{
		try
		{
			//変数宣言
			Maze2D_Anahori_Make maze;				//迷路用クラス
			Scanner sc;								//入力
			int[] inputbox = new int[6];			//入力値を格納する変数
			boolean nonillegal;					//入力が正しかったかの判定を格納する変数
			
			int i;

			//入力を促すメッセージ
			System.out.println("----------------------------------------");
			System.out.println("　　　　　迷路生成プログラム");
			System.out.println("入力値：");
			System.out.println("サイズx, y : スタートx, y : ゴールx, y");
			System.out.println("サイズは奇数、スタート、ゴール座標は偶数");
			System.out.println("あまり極端な値の場合エラーになるかも…");
			System.out.println("----------------------------------------");
			System.out.println("入力してください > ");
			
			//値を入力する。
			sc = new Scanner(System.in);		//入力用インスタンスを作成
			do
			{
				//入力
				for(i = 0; i < inputbox.length; i++)
				{
					inputbox[i] = sc.nextInt();
				}
		
				//入力した値をコンストラクタの引数に入れて迷路のインスタンスを作成
				maze = new Maze2D_Anahori_Make(
						inputbox[0],
						inputbox[1],
						inputbox[2],
						inputbox[3],
						inputbox[4],
						inputbox[5]
					);
				
				//値が正常か確認する。
				nonillegal = maze.getNonIllegal();
				
				//サイズが奇数以外、スタート、ゴール座標が１つでも奇数になっていた、極端な値の場合
				if(nonillegal == false)
				{
					System.out.println("値の範囲が不適切です。もう一度入力してください。");
				}
			//上記の場合もう一度入力させる。
			}while(nonillegal == false);
			sc.close();							//入力用インスタンスを閉じる

			//迷路を出力する。
			maze.disp();
		}
		//整数以外の値、変な値を入れてしまったら
		catch(InputMismatchException e)
		{
			System.out.println("値が不適切です。プログラムを終了します。");
			System.out.println(e);
		}
	}
}
