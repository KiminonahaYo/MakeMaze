package rotation;

//２次元の方向に関するヘッダファイル（上下左右）
public class Rotation_2D
{
	//方向名（すべて整数）
	protected final int rotation_error 	= -1;
	protected final int rotation_up 		= 0;
	protected final int rotation_down 	= 1;
	protected final int rotation_left 	= 2;
	protected final int rotation_right 	= 3;

	//方向名（整数型）から座標に変換
	public PositionStruct2D KindToVector
	(
		int kind					//方向名
	)
	{
		//返却用座標クラス（構造体として使用）
		PositionStruct2D ret = new PositionStruct2D();

		//方向から座標を求める
		switch(kind)
		{
			case rotation_left: 	ret.x = -1; ret.y =  0; break;
			case rotation_right: 	ret.x = +1; ret.y =  0; break;
			case rotation_up: 		ret.x =  0; ret.y = -1; break;
			case rotation_down: 	ret.x =  0; ret.y = +1; break;
			case rotation_error:	ret.x =  0; ret.y =  0; break;
			default:				ret.x =  0; ret.y =  0; break;
		}

		//座標を返す
		return ret;
	}

	//ベクトルから方向名（整数型）に変換
	public	int VectorToKind
	(
		PositionStruct2D position	//座標（向きのベクトル）
	)
	{
		//返却用方向名変数
		int ret;

		//座標から方向名を求める
		if			(position.x == -1 && position.y ==  0)	ret = rotation_left;
		else if	(position.x == +1 && position.y ==  0)	ret = rotation_right;
		else if	(position.x ==  0 && position.y == -1)	ret = rotation_up;
		else if	(position.x ==  0 && position.y == +1)	ret = rotation_down;
		else												ret = rotation_error;

		//方向名を返す
		return ret;
	}
}
