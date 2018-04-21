package io.moonlight.utils.mapper;

import java.text.SimpleDateFormat;

/**
 * Created by qt on 2017/8/15.
 */
public class ConvertTest {

    public void test1() throws Exception {

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-20 19:57:20").getTime());

        //        MAAction maAction = JSON.parseObject("{\n" +
        //                "  \"actionTypeId\": 1,\n" +
        //                "  \"actionTypeLabel\": \"send_coupon\",\n" +
        //                "  \"actionTypeName\": \"send_coupon\",\n" +
        //                "  \"groupId\": 9120,\n" +
        //                "  \"id\": 1040,\n" +
        //                "  \"params\": [\n" +
        //                "    {\n" +
        //                "      \"actionId\": 1040,\n" +
        //                "      \"actionParamTypeId\": 3,\n" +
        //                "      \"createTime\": 1499875200000,\n" +
        //                "      \"createUser\": \"chenning\",\n" +
        //                "      \"ext\": \"\",\n" +
        //                "      \"id\": 5001,\n" +
        //                "      \"isEnableAbtest\": 0,\n" +
        //                "      \"isLeaf\": 0,\n" +
        //                "      \"label\": \"start_time\",\n" +
        //                "      \"parent\": 0,\n" +
        //                "      \"relativeTime\": 0,\n" +
        //                "      \"typeName\": \"start_time\",\n" +
        //                "      \"updateTime\": 1499875200000,\n" +
        //                "      \"updateUser\": \"chenning\",\n" +
        //                "      \"value\": \"1\",\n" +
        //                "      \"valueLabel\": \"立即执行\"\n" +
        //                "    },\n" +
        //                "    {\n" +
        //                "      \"actionId\": 1040,\n" +
        //                "      \"actionParamTypeId\": 1,\n" +
        //                "      \"createTime\": 1499875200000,\n" +
        //                "      \"createUser\": \"chenning\",\n" +
        //                "      \"ext\": \"\",\n" +
        //                "      \"id\": 5002,\n" +
        //                "      \"isEnableAbtest\": 1,\n" +
        //                "      \"isLeaf\": 1,\n" +
        //                "      \"label\": \"batch_no\",\n" +
        //                "      \"parent\": 0,\n" +
        //                "      \"relativeTime\": 0,\n" +
        //                "      \"typeName\": \"batch_no\",\n" +
        //                "      \"updateTime\": 1499875200000,\n" +
        //                "      \"updateUser\": \"chenning\",\n" +
        //                "      \"value\": \"test_eta_broker.params_test\",\n" +
        //                "      \"valueLabel\": \"test_eta_broker.params_test\"\n" +
        //                "    },\n" +
        //                "    {\n" +
        //                "      \"actionId\": 1040,\n" +
        //                "      \"actionParamTypeId\": 22,\n" +
        //                "      \"createTime\": 1499875200000,\n" +
        //                "      \"createUser\": \"chenning\",\n" +
        //                "      \"ext\": \"\",\n" +
        //                "      \"id\": 5003,\n" +
        //                "      \"isEnableAbtest\": 0,\n" +
        //                "      \"isLeaf\": 1,\n" +
        //                "      \"label\": \"isToReferrer\",\n" +
        //                "      \"parent\": 0,\n" +
        //                "      \"relativeTime\": 0,\n" +
        //                "      \"typeName\": \"isToReferrer\",\n" +
        //                "      \"updateTime\": 1499875200000,\n" +
        //                "      \"updateUser\": \"chenning\",\n" +
        //                "      \"value\": \"false\",\n" +
        //                "      \"valueLabel\": \"不是\"\n" +
        //                "    },\n" +
        //                "    {\n" +
        //                "      \"actionId\": 1040,\n" +
        //                "      \"actionParamTypeId\": 5,\n" +
        //                "      \"createTime\": 1499875200000,\n" +
        //                "      \"createUser\": \"chenning\",\n" +
        //                "      \"ext\": \"\",\n" +
        //                "      \"id\": 5004,\n" +
        //                "      \"isEnableAbtest\": 0,\n" +
        //                "      \"isLeaf\": 1,\n" +
        //                "      \"label\": \"action_target\",\n" +
        //                "      \"parent\": 0,\n" +
        //                "      \"relativeTime\": 0,\n" +
        //                "      \"typeName\": \"action_target\",\n" +
        //                "      \"updateTime\": 1499875200000,\n" +
        //                "      \"updateUser\": \"chenning\",\n" +
        //                "      \"value\": \"${recommended}\",\n" +
        //                "      \"valueLabel\": \"默认\"\n" +
        //                "    },\n" +
        //                "    {\n" +
        //                "      \"actionId\": 1040,\n" +
        //                "      \"actionParamTypeId\": 2,\n" +
        //                "      \"createTime\": 1499875200000,\n" +
        //                "      \"createUser\": \"chenning\",\n" +
        //                "      \"ext\": \"\",\n" +
        //                "      \"id\": 5005,\n" +
        //                "      \"isEnableAbtest\": 0,\n" +
        //                "      \"isLeaf\": 1,\n" +
        //                "      \"label\": \"expired_days\",\n" +
        //                "      \"parent\": 0,\n" +
        //                "      \"relativeTime\": 0,\n" +
        //                "      \"typeName\": \"expired_days\",\n" +
        //                "      \"updateTime\": 1499875200000,\n" +
        //                "      \"updateUser\": \"chenning\",\n" +
        //                "      \"value\": \"7\",\n" +
        //                "      \"valueLabel\": \"7\"\n" +
        //                "    }\n" +
        //                "  ]\n" +
        //                "}", MAAction.class);
        //
        //        StopWatch stopWatch = new StopWatch("hi");
        //        stopWatch.start("1");
        //        //for(int i =0;i<1_0000;i++){
        //        System.out.println(JSON.toJSONString(maAction));
        //        Action action = transform(maAction, Action.class, "actionTypeLabel->label", "params->actionParamList", "actionParamTypeId->paramTypeId", "typeName->paramTypeName");
        //        System.out.println(JSON.toJSONString(action));
        //        //}
        //        stopWatch.stop();
        //        stopWatch.start("2");
        //        //for(int i =0;i<1_0000;i++){
        //        //        System.out.println(JSON.toJSONString(maAction));
        //        //        Action action = transform(maAction, new String[]{"actionTypeLabel->label", "params->actionParamList", "actionParamTypeId->paramTypeId", "typeName->paramTypeName"}, Action.class);
        //        //        System.out.println(JSON.toJSONString(action));
        //        //}
        //        stopWatch.stop();
        //        System.out.println(stopWatch.prettyPrint());
    }
    //    StopWatch 'hi': running time (millis) = 3461
    //            -----------------------------------------
    //    ms     %     Task name
    //    -----------------------------------------
    //            03461  100%

    //    StopWatch 'hi': running time (millis) = 10359
    //            -----------------------------------------
    //    ms     %     Task name
    //    -----------------------------------------
    //            10359  100%

    //    StopWatch 'hi': running time (millis) = 79788
    //            -----------------------------------------
    //    ms     %     Task name
    //    -----------------------------------------
    //            79788  100%


}
