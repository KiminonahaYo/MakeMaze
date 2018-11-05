package maze_anahori_2D;

//ランダム関数
import java.util.Random;

import rotation.PositionStruct2D;
import rotation.Rotation_2D;

//迷路作成（穴掘り法）クラス
public class Maze2D_Anahori_Make extends Rotation_2D
{
	//迷路のオブジェクトの種類（定数）
	private  final int maze_road 	= 0;
	private  final int maze_wall 	= 1;
	private  final int maze_start = 2;
	private  final int maze_goal 	= 3;

	private int[][] dataof_maze;

	private int size_x;
	private int size_y;
	private int start_x;
	private int start_y;
	private int goal_x;
	private int goal_y;

	private Random rnd;
	private boolean isnot_illegal;

	//■■■■■■■■■■■■■■パブリックメソッド■■■■■■■■■■■■■■
	
	//コンストラクタ（サイズXとサイズYを指定して迷路を生成する。）
	public Maze2D_Anahori_Make(int MazeSizeX, int MazeSizeY, int MazeStartX, int MazeStartY, int MazeGoalX, int MazeGoalY)
	{
		size_x = MazeSizeX;
		size_y = MazeSizeY;
		start_x = MazeStartX;
		start_y = MazeStartY;
		goal_x = MazeGoalX;
		goal_y = MazeGoalY;

		rnd = new Random();

		isnot_illegal = CreateMaze_CheckNonIllegal();

		if(isnot_illegal)
		{
			CreateMazeMain();
		}
	}

	//表示メソッド（外部から呼び出される。）
	//コンソール表示。
	public void disp()
	{
		if(isnot_illegal)
		{
			int x, y;
			
			//表示（yループが外）
			for(y = 0; y < size_y; y++)
			{
				for(x = 0; x < size_x; x++)
				{
					switch(dataof_maze[x][y])
					{
					case maze_road:  System.out.print("　"); break;	//道は空白
					case maze_wall:  System.out.print("■"); break;	//壁は四角
					case maze_start: System.out.print("Ｓ"); break;	//スタートはＳ
					case maze_goal:  System.out.print("Ｇ"); break;	//ゴールはＧ
					}
				}

				//改行
				System.out.println();
			}
		}
	}
	//■■■■■■ゲッター■■■■■■
	public int getSizeX()
	{
		return size_x;
	}
	public int getSizeY()
	{
		return size_y;
	}
	public int getStartX()
	{
		return start_x;
	}
	public int getStartY()
	{
		return start_y;
	}
	public int getGoalX()
	{
		return goal_x;
	}
	public int getGoalY()
	{
		return goal_y;
	}
	public boolean getNonIllegal()
	{
		return isnot_illegal;
	}

	//■■■■■■■■■■■■■■プライベートメソッド■■■■■■■■■■■■■■

	//迷路を作成する呼び出し元のメソッド
	private void CreateMazeMain()
	{
		dataof_maze = new int[size_x][size_y];

		CreateMazeInit();		//迷路の作成の初期設定
		CreateMaze();			//迷路を作成
		CreateMazeEnd();		//迷路の作成の仕上げ（終了）
	}

	//迷路を初期化するメソッド
	private void CreateMazeInit()
	{
		//迷路作成の土台を作る
		for(int x = 0; x < size_x; x++)
		{
			for(int y = 0; y < size_y; y++)
			{
				dataof_maze[x][y] = CreateMaze_PositionToObjectKind(x, y);
			}
		}

		//迷路座標(2,2)が壁だったら（ゴールでなかったら）
		if(dataof_maze[2][2] == maze_wall)
		{
			dataof_maze[2][2] = maze_road;
		}
	}
	
	private void CreateMazeEnd()
	{
		dataof_maze[start_x][start_y] 	= maze_start;
		dataof_maze[goal_x][goal_y] 	= maze_goal;
	}

	//迷路作成メソッド呼び出し元
	private void CreateMaze()
	{
		CreateMazeSub(start_x, start_y);
		
		for(int x = 2; x <= size_x - 3; x+=2)
		{
			for(int y = 2; y <= size_y - 3; y+=2)
			{
				CreateMazeSub(x, y);
			}
		}

		for(int x = 2; x <= size_x - 3; x+=2)
		{
			for(int y = 2; y <= size_y - 3; y+=2)
			{
				CreateMazeSub(x, y);
			}
		}
	}

	//迷路作成サブメソッド
	private void CreateMazeSub
	(
		int pos_x,		//穴を掘る開始位置x座標
		int pos_y		//穴を掘る開始位置y座標
	)
	{
		PositionStruct2D current 	= new PositionStruct2D();	//今いる座標（今掘っている座標）
		PositionStruct2D vector;								//向き座標
		int i, sign, st;										//一時変数、カウンタ
		int rotation;											//今向いている向き

		//今いる座標をスタートの座標にする
		current.x = pos_x;
		current.y = pos_y;
		
		//スタート位置が壁だったらサブメソッドを終了する。
		if(dataof_maze[pos_x][pos_y] == maze_wall)
			return;

		//原則無限ループとして、処理が終わったらreturnで戻る。
		while(true)
		{
			//最初に向く向きと向きを変える方向（符号）を求める
			st 			= rnd.nextInt() % 4;
			sign 		= rnd.nextBoolean() ? 1 : -1;
			
			//向きは４方向あるので4回向く
			for(i = 0; i < 4; i++)
			{
				rotation = ((st + i) * sign + 4) % 4;
				vector = KindToVector(rotation);

				//向いた先が壁だったら
				if(	dataof_maze[current.x + vector.x * 2][current.y + vector.y * 2] == maze_wall)
				{
					//そこを道にする。
					dataof_maze[current.x + vector.x    ][current.y + vector.y    ] = maze_road;
					dataof_maze[current.x + vector.x * 2][current.y + vector.y * 2] = maze_road;

					//今の領域を移動
					current.x += vector.x * 2;
					current.y += vector.y * 2;
					break;
				}
			}
			//どこを向いても道であった場合、サブメソッドを終了する。
			if(i == 4) return;
		}
	}
	
	//座標を指定して、迷路のオブジェクトの種類を取得する。
	private int
	CreateMaze_PositionToObjectKind
	(
		int pos_x,		//迷路のオブジェクトの種類を取得したい場所のx座標
		int pos_y		//迷路のオブジェクトの種類を取得したい場所のy座標
	)
	{
		if			(pos_x == 0 || pos_y == 0 || pos_x == size_x - 1 || pos_y == size_y - 1)	return maze_road;		//端は道
		else																					return maze_wall;		//端以外（中）は壁
	}

	//コンストラクタの値が適切かチェックするメソッド
	//（NonIllegal＝適切でなくない＝適切）
	private boolean CreateMaze_CheckNonIllegal()
	{
		//座標に関する判定（長すぎるのでいくつかに分けている）
		boolean isgood_start_gusu					= start_x % 2 == 0 			&& start_y % 2 == 0; 			//スタート座標が偶数かどうか
		boolean isgood_start_pos_min				= start_x 	>= 2 			&& start_y 	>= 2;				//スタート座標が適切か（ここでは最小のみを判定）
		boolean isgood_start_pos_max				= start_x 	<= size_x - 3 	&& start_y 	<= size_y - 3;		//スタート座標が適切か（ここでは最大のみを判定）
		boolean isgood_goal_gusu						= goal_x % 2 == 0 			&& goal_y % 2 == 0; 			//ゴール座標が偶数かどうか
		boolean isgood_goal_pos_min					= goal_x 	>= 2 			&& goal_y 	>= 2;				//ゴール座標が適切か（ここでは最小のみを判定）
		boolean isgood_goal_pos_max					= goal_x 	<= size_x - 3 	&& goal_y 	<= size_y - 3;		//ゴール座標が適切か（ここでは最大のみを判定）
		boolean isdifferent_between_startandgoal	= start_x != goal_x 		|| start_y != goal_y;			//スタート座標とゴール座標が異なるかどうか

		//座標が適切かどうか（↑の判定をまとめている。）
		boolean isgood_pos							= isgood_start_gusu && isgood_start_pos_min && isgood_start_pos_max && isgood_goal_gusu && isgood_goal_pos_min && isgood_goal_pos_max && isdifferent_between_startandgoal;

		//サイズが適切かどうか
		boolean isgood_size_kisu 					= size_x % 2 == 1 	&& size_y % 2 == 1;						//迷路のサイズが奇数かどうか
		boolean isgood_size							= size_x >= 5 		&& size_y >= 5;							//迷路のサイズが適切かどうか

		//判定をまとめ、コンストラクタの値が適切かどうかを返却する。
		if(isgood_size_kisu && isgood_size && isgood_pos)
				return true;		//コンストラクタの値は適切
		else 	return false;		//コンストラクタの値は不適切
	}
}
