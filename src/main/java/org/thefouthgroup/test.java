package org.thefouthgroup;

import org.thefouthgroup.database.MyDatabaseUtil;
import org.thefouthgroup.database.MyDruid;
import org.thefouthgroup.entity.Computer;
import org.thefouthgroup.entity.Room;
import org.thefouthgroup.entity.UseRecord;
import org.thefouthgroup.entity.User;
import org.thefouthgroup.manage.TeacherUtil;
import org.thefouthgroup.manage.UserUtil;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class test {
    private static final Logger LOGGER = MyDruid.getLogger();
    public static void main(String[] args) {
        User kkk = MyDatabaseUtil.buildFromDB("2021213196");
        User zxp = MyDatabaseUtil.buildFromDB("2021213209");
        User lt = MyDatabaseUtil.buildFromDB("2021213193");

        zxp.setGroupID(1);//修改信息
        TeacherUtil.changeInformation(kkk, zxp);
        zxp = MyDatabaseUtil.buildFromDB("2021213209");
        System.out.println("zxp:groupID:" + zxp.getGroupID());

        zxp.setGroupID(2);
        TeacherUtil.changeInformation(lt, zxp);
        zxp = MyDatabaseUtil.buildFromDB("2021213209");
        System.out.println("zxp:groupID:" + zxp.getGroupID());

        zxp.setGroupID(2);
        TeacherUtil.changeInformation(kkk, zxp);
        zxp = MyDatabaseUtil.buildFromDB("2021213209");
        System.out.println("zxp:groupID:" + zxp.getGroupID());


        Room room1 = new Room("00006","丹青901",0.2);
        TeacherUtil.addRoom(room1,kkk);
        for (int i = 0;i < 30; i++){
            TeacherUtil.addComputer(new Computer(String.valueOf(i)),room1);
        }
        TeacherUtil.deleteComputer(new Computer("1"));
        TeacherUtil.deleteRoom(room1);

        UserUtil.startComputer(zxp,new Computer("0"));

        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(5000));
        }catch (Exception e){
            LOGGER.info(e.toString());
        }

        UserUtil.endComputer(zxp,new Computer("0"));

        List<UseRecord> records;
        records = TeacherUtil.recordByDate(kkk,"2023-6-21 11:00:00","2023-6-21 15:00:00");
        UseRecord.showRecord(records);
    }
}
