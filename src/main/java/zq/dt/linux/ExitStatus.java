package zq.dt.linux;

/**
 * Created by zhangbo on 2017/9/4.
 */
//退出码（exit status，或exit code）的约定：
public enum ExitStatus {
    Success(0), //表示成功（Zero - Success）
    Error(1), //表示用法不当（Incorrect Usage）
    NotFoundCmd(127), //表示命令没有找到（Command Not Found）
    NotExecutable(126), //表示不是可执行的（Not an executable）
    NotKnow(-1);

    private Integer status;
    ExitStatus(Integer status){
        this.status = status;
    }

    public Integer getValue(){
        return this.status;
    }

    public static ExitStatus getStatus(Integer status){
        for (ExitStatus exitStatus : values()){
            if(exitStatus.getValue() == status){
                return exitStatus;
            }
        }
        return NotKnow;
    }
}
