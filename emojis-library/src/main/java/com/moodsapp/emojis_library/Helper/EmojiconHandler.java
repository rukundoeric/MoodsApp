/*
 * Copyright 2016 Hani Al Momani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moodsapp.emojis_library.Helper;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;

import com.moodsapp.emojis_library.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public final class EmojiconHandler {
    private EmojiconHandler() {
    }

    private static final SparseIntArray sEmojisMap = new SparseIntArray(1029);
    private static final SparseIntArray sSoftbanksMap = new SparseIntArray(471);
    private static Map<String, Integer> sEmojisModifiedMap = new HashMap<>();
    private static Set<String> sInexistentEmojis = new HashSet<>();

    static {
        // People
        sEmojisMap.put(0x1f600, R.drawable.e1511);
        sEmojisMap.put(0x1f62c, R.drawable.e1512);
        sEmojisMap.put(0x1f601, R.drawable.e1513);
        sEmojisMap.put(0x1f602, R.drawable.e1514);
        sEmojisMap.put(0x1f603, R.drawable.e1515);
        sEmojisMap.put(0x1f604, R.drawable.e1516);
        sEmojisMap.put(0x1f605, R.drawable.e1517);
        sEmojisMap.put(0x1f606, R.drawable.e1518);
        sEmojisMap.put(0x1f607, R.drawable.e1520);
        sEmojisMap.put(0x1f609, R.drawable.e1521);
        sEmojisMap.put(0x1f60a, R.drawable.e1522);
        sEmojisMap.put(0x1f642, R.drawable.e1523);
        sEmojisMap.put(0x1f643, R.drawable.e1524);
        sEmojisMap.put(0x263a, R.drawable.e1525);
        sEmojisMap.put(0x1f60b, R.drawable.e1526);
        sEmojisMap.put(0x1f60c, R.drawable.e1527);
        sEmojisMap.put(0x1f60d, R.drawable.e1528);
        sEmojisMap.put(0x1f618, R.drawable.e1529);
        sEmojisMap.put(0x1f617, R.drawable.e1530);
        sEmojisMap.put(0x1f619, R.drawable.e1532);
        sEmojisMap.put(0x1f61a, R.drawable.e1533);
        sEmojisMap.put(0x1f61c, R.drawable.e1534);
        sEmojisMap.put(0x1f61d, R.drawable.e1535);
        sEmojisMap.put(0x1f61b, R.drawable.e1536);
        sEmojisMap.put(0x1f911, R.drawable.e1537);
        sEmojisMap.put(0x1f913, R.drawable.e1538);
        sEmojisMap.put(0x1f60e, R.drawable.e1539);
        sEmojisMap.put(0x1f917, R.drawable.e1540);
        sEmojisMap.put(0x1f60f, R.drawable.e1541);
        sEmojisMap.put(0x1f636, R.drawable.e1542);
        sEmojisMap.put(0x1f610, R.drawable.e1543);
        sEmojisMap.put(0x1f611, R.drawable.e1544);
        sEmojisMap.put(0x1f612, R.drawable.e1545);
        sEmojisMap.put(0x1f644, R.drawable.e1546);
        sEmojisMap.put(0x1f914, R.drawable.e1547);
        sEmojisMap.put(0x1f633, R.drawable.e1548);
        sEmojisMap.put(0x1f61e, R.drawable.e1549);
        sEmojisMap.put(0x1f61f, R.drawable.e1550);
        sEmojisMap.put(0x1f620, R.drawable.e1551);
        sEmojisMap.put(0x1f621, R.drawable.e1552);
        sEmojisMap.put(0x1f614, R.drawable.e1553);
        sEmojisMap.put(0x1f615, R.drawable.e1554);
        sEmojisMap.put(0x1f641, R.drawable.e1555);
        sEmojisMap.put(0x2639, R.drawable.e1556);
        sEmojisMap.put(0x1f623, R.drawable.e1557);
        sEmojisMap.put(0x1f616, R.drawable.e1558);
        sEmojisMap.put(0x1f62b, R.drawable.e1559);
        sEmojisMap.put(0x1f629, R.drawable.e1560);
        sEmojisMap.put(0x1f624, R.drawable.e1561);
        sEmojisMap.put(0x1f62e, R.drawable.e1562);
        sEmojisMap.put(0x1f631, R.drawable.e1895);
        sEmojisMap.put(0x1f628, R.drawable.e1563);
        sEmojisMap.put(0x1f630, R.drawable.e1564);
        sEmojisMap.put(0x1f62f, R.drawable.e1566);
        sEmojisMap.put(0x1f626, R.drawable.e1579);
        sEmojisMap.put(0x1f627, R.drawable.e1819);
        sEmojisMap.put(0x1f622, R.drawable.e1823);
        sEmojisMap.put(0x1f625, R.drawable.e1824);
        sEmojisMap.put(0x1f62a, R.drawable.e1826);
        sEmojisMap.put(0x1f613, R.drawable.e1878);
        sEmojisMap.put(0x1f62d, R.drawable.e1879);
        sEmojisMap.put(0x1f635, R.drawable.e1880);
        sEmojisMap.put(0x1f632, R.drawable.e1893);
        sEmojisMap.put(0x1f910, R.drawable.e1894);
        sEmojisMap.put(0x1f637, R.drawable.e1899);
        sEmojisMap.put(0x1f912, R.drawable.e1900);
        sEmojisMap.put(0x1f915, R.drawable.e2076);
        sEmojisMap.put(0x1f634, R.drawable.e2280);


        sEmojisMap.put(0x1f4a4, R.drawable.e1567);
        sEmojisMap.put(0x1f4a9, R.drawable.e1568);
        sEmojisMap.put(0x1f608, R.drawable.e1569);
        sEmojisMap.put(0x1f47f, R.drawable.e1570);
        sEmojisMap.put(0x1f479, R.drawable.e1571);
        sEmojisMap.put(0x1f47a, R.drawable.e1572);
        sEmojisMap.put(0x1f480, R.drawable.e1573);
        sEmojisMap.put(0x1f47b, R.drawable.e1574);
        sEmojisMap.put(0x1f47d, R.drawable.e1575);
        sEmojisMap.put(0x1f916, R.drawable.e1590);
        sEmojisMap.put(0x1f63a, R.drawable.e1591);
        sEmojisMap.put(0x1f638, R.drawable.e1602);
        sEmojisMap.put(0x1f639, R.drawable.e1603);
        sEmojisMap.put(0x1f63b, R.drawable.e1614);
        sEmojisMap.put(0x1f63c, R.drawable.e1615);
        sEmojisMap.put(0x1f63d, R.drawable.e1629);
        sEmojisMap.put(0x1f640, R.drawable.e1630);
        sEmojisMap.put(0x1f63f, R.drawable.e1647);
        sEmojisMap.put(0x1f63e, R.drawable.e1659);


        sEmojisMap.put(0x1f64c, R.drawable.e0441);
        sEmojisMap.put(0x1f44f, R.drawable.e0508);
        sEmojisMap.put(0x1f44b, R.drawable.e0773);
        sEmojisMap.put(0x1f44d, R.drawable.e0546);
        sEmojisMap.put(0x1f44e, R.drawable.e0558);
        sEmojisMap.put(0x1f44a, R.drawable.e0570);
        sEmojisMap.put(0x270a, R.drawable.e0698);
        sEmojisMap.put(0x270c, R.drawable.e0704);
        sEmojisMap.put(0x1f44c, R.drawable.e0712);
        sEmojisMap.put(0x270b, R.drawable.e0718);
        sEmojisMap.put(0x1f450, R.drawable.e0724);
        sEmojisMap.put(0x1f4aa, R.drawable.e0729);
        sEmojisMap.put(0x1f64f, R.drawable.e0730);
        sEmojisMap.put(0x261d, R.drawable.e0736);
        sEmojisMap.put(0x1f446, R.drawable.e0742);
        sEmojisMap.put(0x1f447, R.drawable.e0748);
        sEmojisMap.put(0x1f448, R.drawable.e0754);
        sEmojisMap.put(0x1f449, R.drawable.e0760);
        sEmojisMap.put(0x1f595, R.drawable.e0766);
        sEmojisMap.put(0x1f590, R.drawable.e0772);
        sEmojisMap.put(0x1f918, R.drawable.e0773);
        sEmojisMap.put(0x270d, R.drawable.e0891);
        sEmojisMap.put(0x1f485, R.drawable.e0892);
        sEmojisMap.put(0x1f444, R.drawable.e0893);
        sEmojisMap.put(0x1f445, R.drawable.e0894);
        sEmojisMap.put(0x1f442, R.drawable.e0895);
        sEmojisMap.put(0x1f443, R.drawable.e0896);
        sEmojisMap.put(0x1f441, R.drawable.e0897);
        sEmojisMap.put(0x1f440, R.drawable.e0898);
        sEmojisMap.put(0x1f464, R.drawable.e0899);
        sEmojisMap.put(0x1f465, R.drawable.e0900);
        sEmojisMap.put(0x1f5e3, R.drawable.e0901);
        sEmojisMap.put(0x1f476, R.drawable.e0902);
        sEmojisMap.put(0x1f466, R.drawable.e0903);
        sEmojisMap.put(0x1f467, R.drawable.e0904);
        sEmojisMap.put(0x1f468, R.drawable.e0905);
        sEmojisMap.put(0x1f469, R.drawable.e0906);
        sEmojisMap.put(0x1f471, R.drawable.e0907);
        sEmojisMap.put(0x1f474, R.drawable.e0908);
        sEmojisMap.put(0x1f475, R.drawable.e0909);
        sEmojisMap.put(0x1f472, R.drawable.e0910);
        sEmojisMap.put(0x1f473, R.drawable.e0911);
        sEmojisMap.put(0x1f46e, R.drawable.e0912);
        sEmojisMap.put(0x1f477, R.drawable.e0913);
        sEmojisMap.put(0x1f482, R.drawable.e0914);
        sEmojisMap.put(0x1f575, R.drawable.e0915);
        sEmojisMap.put(0x1f385, R.drawable.e0916);
        sEmojisMap.put(0x1f47c, R.drawable.e0917);
        sEmojisMap.put(0x1f478, R.drawable.e0918);
        sEmojisMap.put(0x1f470, R.drawable.e0919);
        sEmojisMap.put(0x1f6b6, R.drawable.e0920);
        sEmojisMap.put(0x1f3c3, R.drawable.e0921);
        sEmojisMap.put(0x1f483, R.drawable.e0922);
        sEmojisMap.put(0x1f46f, R.drawable.e0923);
        sEmojisMap.put(0x2728, R.drawable.e0924);
        sEmojisMap.put(0x1f31f, R.drawable.e1010);
        sEmojisMap.put(0x1f4ab, R.drawable.e1011);
        sEmojisMap.put(0x1f4a2, R.drawable.e1012);
        sEmojisMap.put(0x1f46b, R.drawable.e1013);
        sEmojisMap.put(0x1f46c, R.drawable.e1014);
        sEmojisMap.put(0x1f46d, R.drawable.e1015);
        sEmojisMap.put(0x1f647, R.drawable.e1016);
        sEmojisMap.put(0x1f481, R.drawable.e1017);
        sEmojisMap.put(0x1f645, R.drawable.e1018);
        sEmojisMap.put(0x1f646, R.drawable.e1019);
        sEmojisMap.put(0x1f64b, R.drawable.e1020);
        sEmojisMap.put(0x1f64e, R.drawable.e1021);
        sEmojisMap.put(0x1f64d, R.drawable.e1022);
        sEmojisMap.put(0x1f487, R.drawable.e1023);
        sEmojisMap.put(0x1f486, R.drawable.e1024);
        sEmojisMap.put(0x1f491, R.drawable.e1025);
        sEmojisMap.put(0x1f48f, R.drawable.e1026);
        sEmojisMap.put(0x1f46a, R.drawable.e1027);
        sEmojisMap.put(0x1f45a, R.drawable.e1028);
        sEmojisMap.put(0x1f455, R.drawable.e1029);
        sEmojisMap.put(0x1f456, R.drawable.e1030);
        sEmojisMap.put(0x1f454, R.drawable.e1031);
        sEmojisMap.put(0x1f457, R.drawable.e1032);
        sEmojisMap.put(0x1f459, R.drawable.e1033);
        sEmojisMap.put(0x1f458, R.drawable.e1034);
        sEmojisMap.put(0x1f484, R.drawable.e1035);
        sEmojisMap.put(0x1f48b, R.drawable.e1036);
        sEmojisMap.put(0x1f463, R.drawable.e1037);
        sEmojisMap.put(0x1f460, R.drawable.e1038);
        sEmojisMap.put(0x1f461, R.drawable.e1039);
        sEmojisMap.put(0x1f462, R.drawable.e1040);
        sEmojisMap.put(0x1f45e, R.drawable.e1041);
        sEmojisMap.put(0x1f45f, R.drawable.e1074);
        sEmojisMap.put(0x1f452, R.drawable.e1085);
        sEmojisMap.put(0x1f3a9, R.drawable.e1086);
        sEmojisMap.put(0x1f393, R.drawable.e1104);
        sEmojisMap.put(0x1f451, R.drawable.e1110);
        sEmojisMap.put(0x26d1, R.drawable.e1116);
        sSoftbanksMap.put(0xe43a, R.drawable.e1213);
        sEmojisMap.put(0x1f45d, R.drawable.e1122);
        sEmojisMap.put(0x1f45b, R.drawable.e1133);
        sEmojisMap.put(0x1f45c, R.drawable.e1134);
        sEmojisMap.put(0x1f4bc, R.drawable.e1201);
        sEmojisMap.put(0x1f453, R.drawable.e1202);
        sEmojisMap.put(0x1f576, R.drawable.e1213);
        sEmojisMap.put(0x1f48d, R.drawable.e1214);
        sEmojisMap.put(0x1f302, R.drawable.e1636);



        // Nature
        sEmojisMap.put(0x1f436, R.drawable.e0305);
        sEmojisMap.put(0x1f431, R.drawable.e0313);
        sEmojisMap.put(0x1f42d, R.drawable.e0318);
        sEmojisMap.put(0x1f439, R.drawable.e0319);
        sEmojisMap.put(0x1f430, R.drawable.e0320);
        sEmojisMap.put(0x1f43b, R.drawable.e0321);
        sEmojisMap.put(0x1f43c, R.drawable.e0322);
        sEmojisMap.put(0x1f428, R.drawable.e0323);
        sEmojisMap.put(0x1f42f, R.drawable.e0324);
        sEmojisMap.put(0x1f981, R.drawable.e0325);
        sEmojisMap.put(0x1f42e, R.drawable.e0326);
        sEmojisMap.put(0x1f437, R.drawable.e0327);
        sEmojisMap.put(0x1f43d, R.drawable.e0328);
        sEmojisMap.put(0x1f438, R.drawable.e0329);
        sEmojisMap.put(0x1f419, R.drawable.e0330);
        sEmojisMap.put(0x1f435, R.drawable.e0331);
        sEmojisMap.put(0x1f648, R.drawable.e0332);
        sEmojisMap.put(0x1f649, R.drawable.e0333);
        sEmojisMap.put(0x1f64a, R.drawable.e0334);
        sEmojisMap.put(0x1f412, R.drawable.e0335);
        sEmojisMap.put(0x1f414, R.drawable.e0336);
        sEmojisMap.put(0x1f427, R.drawable.e0337);
        sEmojisMap.put(0x1f426, R.drawable.e0338);
        sEmojisMap.put(0x1f424, R.drawable.e0339);
        sEmojisMap.put(0x1f423, R.drawable.e0340);
        sEmojisMap.put(0x1f425, R.drawable.e0341);
        sEmojisMap.put(0x1f43a, R.drawable.e0342);
        sEmojisMap.put(0x1f417, R.drawable.e0343);
        sEmojisMap.put(0x1f434, R.drawable.e0344);
        sEmojisMap.put(0x1f984, R.drawable.e0345);
        sEmojisMap.put(0x1f41d, R.drawable.e0346);
        sEmojisMap.put(0x1f41b, R.drawable.e0347);
        sEmojisMap.put(0x1f40c, R.drawable.e0354);
        sEmojisMap.put(0x1f41e, R.drawable.e0355);
        sEmojisMap.put(0x1f41c, R.drawable.e0356);
        sEmojisMap.put(0x1f577, R.drawable.e0357);
        sEmojisMap.put(0x1f982, R.drawable.e0358);
        sEmojisMap.put(0x1f980, R.drawable.e0359);
        sEmojisMap.put(0x1f40d, R.drawable.e0360);
        sEmojisMap.put(0x1f422, R.drawable.e0361);
        sEmojisMap.put(0x1f420, R.drawable.e0362);
        sEmojisMap.put(0x1f41f, R.drawable.e0363);
        sEmojisMap.put(0x1f421, R.drawable.e0364);
        sEmojisMap.put(0x1f42c, R.drawable.e0365);
        sEmojisMap.put(0x1f433, R.drawable.e0366);
        sEmojisMap.put(0x1f40b, R.drawable.e0367);
        sEmojisMap.put(0x1f40a, R.drawable.e0368);
        sEmojisMap.put(0x1f406, R.drawable.e0369);
        sEmojisMap.put(0x1f405, R.drawable.e0370);
        sEmojisMap.put(0x1f403, R.drawable.e0371);
        sEmojisMap.put(0x1f402, R.drawable.e0447);
        sEmojisMap.put(0x1f404, R.drawable.e0449);
        sEmojisMap.put(0x1f42a, R.drawable.e0450);
        sEmojisMap.put(0x1f42b, R.drawable.e0452);
        sEmojisMap.put(0x1f418, R.drawable.e0453);
        sEmojisMap.put(0x1f410, R.drawable.e0483);
        sEmojisMap.put(0x1f40f, R.drawable.e0580);
        sEmojisMap.put(0x1f411, R.drawable.e0581);
        sEmojisMap.put(0x1f40e, R.drawable.e0591);
        sEmojisMap.put(0x1f416, R.drawable.e0594);
        sEmojisMap.put(0x1f400, R.drawable.e0600);
        sEmojisMap.put(0x1f401, R.drawable.e0601);
        sEmojisMap.put(0x1f413, R.drawable.e0602);
        sEmojisMap.put(0x1f983, R.drawable.e0603);
        sEmojisMap.put(0x1f54a, R.drawable.e0604);
        sEmojisMap.put(0x1f415, R.drawable.e0605);
        sEmojisMap.put(0x1f429, R.drawable.e0606);
        sEmojisMap.put(0x1f408, R.drawable.e0607);
        sEmojisMap.put(0x1f407, R.drawable.e0608);
        sEmojisMap.put(0x1f43f, R.drawable.e0615);
        sEmojisMap.put(0x1f43e, R.drawable.e0616);
        sEmojisMap.put(0x1f409, R.drawable.e0617);
        sEmojisMap.put(0x1f432, R.drawable.e0620);
        sEmojisMap.put(0x1f335, R.drawable.e0621);
        sEmojisMap.put(0x1f332, R.drawable.e0622);
        sEmojisMap.put(0x1f333, R.drawable.e0623);
        sEmojisMap.put(0x1f334, R.drawable.e0624);
        sEmojisMap.put(0x1f33f, R.drawable.e0625);
        sEmojisMap.put(0x1f340, R.drawable.e0688);
        sEmojisMap.put(0x1f38d, R.drawable.e0690);
        sEmojisMap.put(0x1f38b, R.drawable.e0691);
        sEmojisMap.put(0x1f343, R.drawable.e0776);
        sEmojisMap.put(0x1f342, R.drawable.e0777);
        sEmojisMap.put(0x1f341, R.drawable.e0778);
        sEmojisMap.put(0x1f33e, R.drawable.e0779);
        sEmojisMap.put(0x1f33a, R.drawable.e0780);
        sEmojisMap.put(0x1f33b, R.drawable.e0781);
        sEmojisMap.put(0x1f339, R.drawable.e0782);
        sEmojisMap.put(0x1f337, R.drawable.e0783);
        sEmojisMap.put(0x1f33c, R.drawable.e1223);
        sEmojisMap.put(0x1f338, R.drawable.e1227);
        sEmojisMap.put(0x1f490, R.drawable.e1239);
        sEmojisMap.put(0x1f344, R.drawable.e1240);
        sEmojisMap.put(0x1f330, R.drawable.e1241);
        sEmojisMap.put(0x1f383, R.drawable.e1242);
        sEmojisMap.put(0x1f41a, R.drawable.e1243);
        sEmojisMap.put(0x1f578, R.drawable.e1244);
        sEmojisMap.put(0x1f30d, R.drawable.e1245);
        sEmojisMap.put(0x1f30e, R.drawable.e1246);
        sEmojisMap.put(0x1f30f, R.drawable.e1247);
        sEmojisMap.put(0x1f315, R.drawable.e1255);
        sEmojisMap.put(0x1f316, R.drawable.e1256);
        sEmojisMap.put(0x1f317, R.drawable.e1257);
        sEmojisMap.put(0x1f318, R.drawable.e1258);
        sEmojisMap.put(0x1f311, R.drawable.e1259);
        sEmojisMap.put(0x1f312, R.drawable.e1260);
        sEmojisMap.put(0x1f313, R.drawable.e1261);
        sEmojisMap.put(0x1f314, R.drawable.e1271);
        sEmojisMap.put(0x1f31a, R.drawable.e1272);
        sEmojisMap.put(0x1f31d, R.drawable.e1307);
        sEmojisMap.put(0x1f31b, R.drawable.e1325);
        sSoftbanksMap.put(0xe033, R.drawable.e1505);
        sSoftbanksMap.put(0xe033, R.drawable.e1247);
        sSoftbanksMap.put(0xe033, R.drawable.e1260);
        sSoftbanksMap.put(0xe033, R.drawable.e1377);
        sSoftbanksMap.put(0xe033, R.drawable.e1508);
        sEmojisMap.put(0x1f31c, R.drawable.e1326);
        sEmojisMap.put(0x1f31e, R.drawable.e1336);
        sEmojisMap.put(0x1f319, R.drawable.e1337);
        sEmojisMap.put(0x2b50, R.drawable.e1351);
        sEmojisMap.put(0x2604, R.drawable.e1377);
        sEmojisMap.put(0x2600, R.drawable.e1431);
        sEmojisMap.put(0x1f324, R.drawable.e1490);
        sEmojisMap.put(0x26c5, R.drawable.e1505);
        sEmojisMap.put(0x1f325, R.drawable.e1506);
        sEmojisMap.put(0x1f326, R.drawable.e1507);
        sEmojisMap.put(0x2601, R.drawable.e1508);
        sEmojisMap.put(0x1f327, R.drawable.e1509);
        sEmojisMap.put(0x26c8, R.drawable.e1715);
        sEmojisMap.put(0x1f329, R.drawable.e2030);
        sEmojisMap.put(0x26a1, R.drawable.e2032);
        sEmojisMap.put(0x1f4a5, R.drawable.e2040);
        sEmojisMap.put(0x2744, R.drawable.e2043);
        sEmojisMap.put(0x1f328, R.drawable.e2045);
        sEmojisMap.put(0x2603, R.drawable.e2256);
        sEmojisMap.put(0x26c4, R.drawable.e2257);
        sEmojisMap.put(0x1f32c, R.drawable.e2260);
        sEmojisMap.put(0x1f4a8, R.drawable.e2263);
        sEmojisMap.put(0x1f32a, R.drawable.e2265);
        sEmojisMap.put(0x1f32b, R.drawable.e2321);
        sEmojisMap.put(0x2602, R.drawable.e2322);
        sEmojisMap.put(0x2614, R.drawable.e2330);
        sEmojisMap.put(0x1f4a6, R.drawable.e2332);
        sEmojisMap.put(0x1f4a7, R.drawable.e2333);
        sEmojisMap.put(0x1f30a, R.drawable.e2390);
///NATURE


        /////FOOD

        sEmojisMap.put(0x1f34f, R.drawable.e0348);
        sEmojisMap.put(0x1f34e, R.drawable.e0349);
        sEmojisMap.put(0x1f350, R.drawable.e0350);
        sEmojisMap.put(0x1f34a, R.drawable.e0351);
        sEmojisMap.put(0x1f34b, R.drawable.e0372);
        sEmojisMap.put(0x1f34c, R.drawable.e0373);
        sEmojisMap.put(0x1f349, R.drawable.e0374);
        sEmojisMap.put(0x1f347, R.drawable.e0375);
        sEmojisMap.put(0x1f353, R.drawable.e0376);
        sEmojisMap.put(0x1f348, R.drawable.e0377);
        sEmojisMap.put(0x1f352, R.drawable.e0378);
        sEmojisMap.put(0x1f351, R.drawable.e0379);
        sEmojisMap.put(0x1f34d, R.drawable.e0380);
        sEmojisMap.put(0x1f345, R.drawable.e0381);
        sEmojisMap.put(0x1f346, R.drawable.e0382);
        sEmojisMap.put(0x1f336, R.drawable.e0383);
        sEmojisMap.put(0x1f33d, R.drawable.e0384);
        sEmojisMap.put(0x1f360, R.drawable.e0385);
        sEmojisMap.put(0x1f36f, R.drawable.e0386);
        sEmojisMap.put(0x1f35e, R.drawable.e0387);
        sEmojisMap.put(0x1f9c0, R.drawable.e0388);
        sEmojisMap.put(0x1f357, R.drawable.e0389);
        sEmojisMap.put(0x1f356, R.drawable.e0390);
        sEmojisMap.put(0x1f364, R.drawable.e0392);
        sEmojisMap.put(0x1f373, R.drawable.e0393);
        sEmojisMap.put(0x1f354, R.drawable.e0394);
        sEmojisMap.put(0x1f35f, R.drawable.e0395);
        sEmojisMap.put(0x1f32d, R.drawable.e0396);
        sEmojisMap.put(0x1f355, R.drawable.e0397);
        sEmojisMap.put(0x1f35d, R.drawable.e0398);
        sEmojisMap.put(0x1f32e, R.drawable.e0399);
        sEmojisMap.put(0x1f32f, R.drawable.e0400);
        sEmojisMap.put(0x1f35c, R.drawable.e0401);
        sEmojisMap.put(0x1f372, R.drawable.e0402);
        sEmojisMap.put(0x1f365, R.drawable.e0403);
        sEmojisMap.put(0x1f363, R.drawable.e0404);
        sEmojisMap.put(0x1f371, R.drawable.e0405);
        sEmojisMap.put(0x1f35b, R.drawable.e0406);
        sEmojisMap.put(0x1f359, R.drawable.e0407);
        sEmojisMap.put(0x1f35a, R.drawable.e0408);
        sEmojisMap.put(0x1f358, R.drawable.e0409);
        sEmojisMap.put(0x1f362, R.drawable.e0410);
        sEmojisMap.put(0x1f361, R.drawable.e0411);
        sEmojisMap.put(0x1f367, R.drawable.e0412);
        sEmojisMap.put(0x1f368, R.drawable.e0413);
        sEmojisMap.put(0x1f366, R.drawable.e0414);
        sEmojisMap.put(0x1f370, R.drawable.e0415);
        sEmojisMap.put(0x1f382, R.drawable.e0416);
        sEmojisMap.put(0x1f36e, R.drawable.e0417);
        sEmojisMap.put(0x1f36c, R.drawable.e0418);
        sEmojisMap.put(0x1f36d, R.drawable.e0419);
        sEmojisMap.put(0x1f36b, R.drawable.e0420);
        sEmojisMap.put(0x1f37f, R.drawable.e0421);
        sEmojisMap.put(0x1f369, R.drawable.e0422);
        sEmojisMap.put(0x1f36a, R.drawable.e0423);
        sEmojisMap.put(0x1f37a, R.drawable.e0424);
        sEmojisMap.put(0x1f37b, R.drawable.e0425);
        sEmojisMap.put(0x1f377, R.drawable.e0426);
        sEmojisMap.put(0x1f378, R.drawable.e0427);
        sEmojisMap.put(0x1f379, R.drawable.e0428);
        sEmojisMap.put(0x1f37e, R.drawable.e0429);
        sEmojisMap.put(0x1f376, R.drawable.e0430);
        sEmojisMap.put(0x1f375, R.drawable.e0431);
        sEmojisMap.put(0x2615, R.drawable.e0432);
        sEmojisMap.put(0x1f37c, R.drawable.e0433);
        sEmojisMap.put(0x1f374, R.drawable.e0434);
        //////FOOOD


        //sport
        sEmojisMap.put(0x26bd, R.drawable.e0454);
        sEmojisMap.put(0x1f3c0, R.drawable.e0456);
        sEmojisMap.put(0x1f3c8, R.drawable.e0457);
        sEmojisMap.put(0x26be, R.drawable.e0477);
        sEmojisMap.put(0x1f3be, R.drawable.e0478);
        sEmojisMap.put(0x1f3d0, R.drawable.e0480);
        sEmojisMap.put(0x1f3c9, R.drawable.e0481);
        sEmojisMap.put(0x1f3b1, R.drawable.e0482);
        sEmojisMap.put(0x26f3, R.drawable.e0492);
        sEmojisMap.put(0x1f3cc, R.drawable.e0493);
        sEmojisMap.put(0x1f3d3, R.drawable.e0494);
        sEmojisMap.put(0x1f3f8, R.drawable.e0495);
        sEmojisMap.put(0x1f3d2, R.drawable.e0496);
        sEmojisMap.put(0x1f3d1, R.drawable.e0497);
        sEmojisMap.put(0x1f3cf, R.drawable.e0498);
        sEmojisMap.put(0x1f3bf, R.drawable.e0499);
        sEmojisMap.put(0x26f7, R.drawable.e0532);
        sEmojisMap.put(0x1f3c2, R.drawable.e0533);
        sEmojisMap.put(0x26f8, R.drawable.e0572);
        sEmojisMap.put(0x1f3f9, R.drawable.e0575);
        sEmojisMap.put(0x1f3a3, R.drawable.e0576);
        sEmojisMap.put(0x1f6a3, R.drawable.e0577);
        sEmojisMap.put(0x1f3ca, R.drawable.e0578);
        sEmojisMap.put(0x1f3c4, R.drawable.e0579);
        sEmojisMap.put(0x1f6c0, R.drawable.e0582);
        sEmojisMap.put(0x26f9, R.drawable.e0618);
        sEmojisMap.put(0x1f3cb, R.drawable.e0619);
        sEmojisMap.put(0x1f6b4, R.drawable.e0626);
        sEmojisMap.put(0x1f6b5, R.drawable.e0687);
        sEmojisMap.put(0x1f3c7, R.drawable.e1702);
        sEmojisMap.put(0x1f574, R.drawable.e1708);
        sEmojisMap.put(0x1f3c6, R.drawable.e1709);
        sEmojisMap.put(0x1f3bd, R.drawable.e1714);
        sEmojisMap.put(0x1f3c5, R.drawable.e1731);
        sEmojisMap.put(0x1f396, R.drawable.e1738);
        sEmojisMap.put(0x1f397, R.drawable.e1743);
        sEmojisMap.put(0x1f3f5, R.drawable.e1755);
        sSoftbanksMap.put(0xe125, R.drawable.e0626);
        sEmojisMap.put(0x1f39f, R.drawable.e1757);
        sEmojisMap.put(0x1f3ad, R.drawable.e1761);
        sEmojisMap.put(0x1f3a8, R.drawable.e1769);
        sEmojisMap.put(0x1f3aa, R.drawable.e1957);
        sEmojisMap.put(0x1f3a4, R.drawable.e1966);
        sEmojisMap.put(0x1f3a7, R.drawable.e1968);
        sEmojisMap.put(0x1f3bc, R.drawable.e1969);
        sEmojisMap.put(0x1f3b9, R.drawable.e1970);
        sEmojisMap.put(0x1f3b7, R.drawable.e1971);
        sEmojisMap.put(0x1f3ba, R.drawable.e1984);
        sEmojisMap.put(0x1f3bb, R.drawable.e1997);
        sEmojisMap.put(0x1f3b8, R.drawable.e1999);
        sEmojisMap.put(0x1f3ac, R.drawable.e2009);
        sEmojisMap.put(0x1f3ae, R.drawable.e2010);
        sEmojisMap.put(0x1f47e, R.drawable.e2122);
        sEmojisMap.put(0x1f3af, R.drawable.e2125);
        sEmojisMap.put(0x1f3b2, R.drawable.e2132);
        sEmojisMap.put(0x1f3b0, R.drawable.e2336);
        sEmojisMap.put(0x1f3b3, R.drawable.e2343);


        //sport



///CARS

        sEmojisMap.put(0x1f697, R.drawable.e0306);
        sEmojisMap.put(0x1f695, R.drawable.e0308);
        sEmojisMap.put(0x1f699, R.drawable.e0309);
        sEmojisMap.put(0x1f68c, R.drawable.e0310);
        sEmojisMap.put(0x1f68e, R.drawable.e0311);
        sEmojisMap.put(0x1f3ce, R.drawable.e0312);
        sEmojisMap.put(0x1f693, R.drawable.e0448);
        sEmojisMap.put(0x1f691, R.drawable.e0455);
        sEmojisMap.put(0x1f692, R.drawable.e0460);
        sEmojisMap.put(0x1f690, R.drawable.e0462);
        sEmojisMap.put(0x1f69a, R.drawable.e0463);
        sEmojisMap.put(0x1f69b, R.drawable.e0471);
        sEmojisMap.put(0x1f69c, R.drawable.e0472);
        sEmojisMap.put(0x1f3cd, R.drawable.e0473);
        sEmojisMap.put(0x1f6b2, R.drawable.e0474);
        sEmojisMap.put(0x1f6a8, R.drawable.e0476);
        sEmojisMap.put(0x1f694, R.drawable.e0479);
        sEmojisMap.put(0x1f68d, R.drawable.e0573);
        sEmojisMap.put(0x1f698, R.drawable.e0574);
        sEmojisMap.put(0x1f696, R.drawable.e0586);
        sEmojisMap.put(0x1f6a1, R.drawable.e0587);
        sEmojisMap.put(0x1f6a0, R.drawable.e0592);
        sEmojisMap.put(0x1f69f, R.drawable.e0593);
        sEmojisMap.put(0x1f68b, R.drawable.e0595);
        sEmojisMap.put(0x1f683, R.drawable.e0596);
        sEmojisMap.put(0x1f69d, R.drawable.e0597);
        sEmojisMap.put(0x1f684, R.drawable.e0598);
        sEmojisMap.put(0x1f685, R.drawable.e0784);
        sEmojisMap.put(0x1f688, R.drawable.e0785);
        sEmojisMap.put(0x1f69e, R.drawable.e0787);
        sEmojisMap.put(0x1f682, R.drawable.e1225);
        sEmojisMap.put(0x1f686, R.drawable.e1433);
        sEmojisMap.put(0x1f687, R.drawable.e1452);
        sEmojisMap.put(0x1f68a, R.drawable.e1453);
        sEmojisMap.put(0x1f689, R.drawable.e1454);
        sEmojisMap.put(0x1f681, R.drawable.e1455);
        sEmojisMap.put(0x1f6e9, R.drawable.e1494);
        sEmojisMap.put(0x2708, R.drawable.e1501);
        sEmojisMap.put(0x1f6eb, R.drawable.e1503);
        sEmojisMap.put(0x1f6ec, R.drawable.e1504);
        sEmojisMap.put(0x26f5, R.drawable.e1510);
        sEmojisMap.put(0x1f6e5, R.drawable.e1667);
        sEmojisMap.put(0x1f6a4, R.drawable.e1668);
        sEmojisMap.put(0x26f4, R.drawable.e1669);
        sEmojisMap.put(0x1f6f3, R.drawable.e1670);
        sEmojisMap.put(0x1f680, R.drawable.e1671);
        sEmojisMap.put(0x1f6f0, R.drawable.e1672);
        sEmojisMap.put(0x1f4ba, R.drawable.e1673);
        sEmojisMap.put(0x2693, R.drawable.e1674);
        sEmojisMap.put(0x1f6a7, R.drawable.e1675);
        sEmojisMap.put(0x26fd, R.drawable.e1676);
        sEmojisMap.put(0x1f68f, R.drawable.e1677);
        sEmojisMap.put(0x1f6a6, R.drawable.e1678);
        sEmojisMap.put(0x1f6a5, R.drawable.e1679);
        sEmojisMap.put(0x1f3c1, R.drawable.e1680);
        sEmojisMap.put(0x1f6a2, R.drawable.e1681);
        sEmojisMap.put(0x1f3a2, R.drawable.e1682);
        sEmojisMap.put(0x1f3a0, R.drawable.e1683);
        sEmojisMap.put(0x1f3d7, R.drawable.e1684);
        sEmojisMap.put(0x1f301, R.drawable.e1685);
        sEmojisMap.put(0x1f5fc, R.drawable.e1686);
        sEmojisMap.put(0x1f3ed, R.drawable.e1687);
        sEmojisMap.put(0x26f2, R.drawable.e1688);
        sSoftbanksMap.put(0xe44c, R.drawable.e1704);
        sEmojisMap.put(0x26f0, R.drawable.e1689);
        sEmojisMap.put(0x1f3d4, R.drawable.e1690);
        sEmojisMap.put(0x1f5fb, R.drawable.e1691);
        sEmojisMap.put(0x1f30b, R.drawable.e1692);
        sEmojisMap.put(0x1f5fe, R.drawable.e1693);
        sEmojisMap.put(0x1f3d5, R.drawable.e1694);
        sEmojisMap.put(0x26fa, R.drawable.e1695);
        sEmojisMap.put(0x1f3de, R.drawable.e1696);
        sEmojisMap.put(0x1f6e3, R.drawable.e1697);
        sEmojisMap.put(0x1f6e4, R.drawable.e1698);
        sEmojisMap.put(0x1f305, R.drawable.e1699);
        sEmojisMap.put(0x1f304, R.drawable.e1700);
        sEmojisMap.put(0x1f3dc, R.drawable.e1701);
        sEmojisMap.put(0x1f3d6, R.drawable.e1703);
        sEmojisMap.put(0x1f3dd, R.drawable.e1704);
        sEmojisMap.put(0x1f307, R.drawable.e1705);
        sEmojisMap.put(0x1f306, R.drawable.e1706);
        sEmojisMap.put(0x1f3d9, R.drawable.e1707);
        sEmojisMap.put(0x1f320, R.drawable.e1710);
        sEmojisMap.put(0x1f309, R.drawable.e1716);
        sEmojisMap.put(0x1f30c, R.drawable.e1717);
        sEmojisMap.put(0x1f386, R.drawable.e1718);
        sEmojisMap.put(0x1f387, R.drawable.e1719);
        sSoftbanksMap.put(0xe44c, R.drawable.e1809);
        sEmojisMap.put(0x1f3d8, R.drawable.e1720);
        sEmojisMap.put(0x1f3f0, R.drawable.e1729);
        sEmojisMap.put(0x1f3ef, R.drawable.e1732);
        sEmojisMap.put(0x1f3df, R.drawable.e1742);
        sEmojisMap.put(0x1f5fd, R.drawable.e1744);
        sEmojisMap.put(0x1f3e0, R.drawable.e1790);
        sEmojisMap.put(0x1f3e1, R.drawable.e1807);
        sEmojisMap.put(0x1f3da, R.drawable.e1808);
        sEmojisMap.put(0x1f3e2, R.drawable.e1809);
        sEmojisMap.put(0x1f3ec, R.drawable.e1810);
        sEmojisMap.put(0x1f3e3, R.drawable.e1811);
        sEmojisMap.put(0x1f3e4, R.drawable.e1812);
        sEmojisMap.put(0x1f3e5, R.drawable.e1813);
        sEmojisMap.put(0x1f3e6, R.drawable.e1814);
        sEmojisMap.put(0x1f3e8, R.drawable.e1815);
        sEmojisMap.put(0x1f3ea, R.drawable.e1816);
        sEmojisMap.put(0x1f3eb, R.drawable.e1817);
        sEmojisMap.put(0x1f3e9, R.drawable.e1818);
        sEmojisMap.put(0x1f492, R.drawable.e1825);
        sEmojisMap.put(0x1f3db, R.drawable.e2329);
        sEmojisMap.put(0x26ea, R.drawable.e2334);
        sEmojisMap.put(0x1f54c, R.drawable.e2335);
        sEmojisMap.put(0x1f54b, R.drawable.e2355);
        ///


/// ELECT

        sEmojisMap.put(0x231a, R.drawable.e0307);
        sEmojisMap.put(0x1f4f1, R.drawable.e0314);
        sEmojisMap.put(0x1f4f2, R.drawable.e0315);
        sEmojisMap.put(0x1f4bb, R.drawable.e0316);
        sEmojisMap.put(0x2328, R.drawable.e0317);
        sEmojisMap.put(0x1f5a5, R.drawable.e0442);
        sEmojisMap.put(0x1f5a8, R.drawable.e0443);
        sEmojisMap.put(0x1f5b1, R.drawable.e0444);
        sEmojisMap.put(0x1f5b2, R.drawable.e0445);
        sEmojisMap.put(0x1f579, R.drawable.e0446);
        sEmojisMap.put(0x1f5dc, R.drawable.e0451);
        sEmojisMap.put(0x1f4bd, R.drawable.e0458);
        sEmojisMap.put(0x1f4be, R.drawable.e0459);
        sEmojisMap.put(0x1f4bf, R.drawable.e0461);
        sEmojisMap.put(0x1f4c0, R.drawable.e0464);
        sEmojisMap.put(0x1f4fc, R.drawable.e0465);
        sEmojisMap.put(0x1f4f7, R.drawable.e0466);
        sEmojisMap.put(0x1f4f8, R.drawable.e0467);
        sEmojisMap.put(0x1f4f9, R.drawable.e0468);
        sEmojisMap.put(0x1f3a5, R.drawable.e0469);
        sEmojisMap.put(0x1f4fd, R.drawable.e0470);
        sEmojisMap.put(0x1f39e, R.drawable.e0475);
        sEmojisMap.put(0x1f4de, R.drawable.e0484);
        sEmojisMap.put(0x260e, R.drawable.e0485);
        sEmojisMap.put(0x1f4df, R.drawable.e0486);
        sEmojisMap.put(0x1f4e0, R.drawable.e0487);
        sEmojisMap.put(0x1f4fa, R.drawable.e0488);
        sEmojisMap.put(0x1f4fb, R.drawable.e0489);
        sEmojisMap.put(0x1f399, R.drawable.e0490);
        sEmojisMap.put(0x1f39a, R.drawable.e0491);
        sEmojisMap.put(0x1f39b, R.drawable.e0583);
        sEmojisMap.put(0x23f1, R.drawable.e0584);
        sEmojisMap.put(0x23f2, R.drawable.e0585);
        sEmojisMap.put(0x23f0, R.drawable.e0588);
        sEmojisMap.put(0x1f570, R.drawable.e0589);
        sEmojisMap.put(0x23f3, R.drawable.e0590);
        sEmojisMap.put(0x231b, R.drawable.e0594);
        sEmojisMap.put(0x1f4e1, R.drawable.e0600);
        sEmojisMap.put(0x1f50b, R.drawable.e0601);
        sEmojisMap.put(0x1f50c, R.drawable.e0602);
        sEmojisMap.put(0x1f4a1, R.drawable.e0603);
        sEmojisMap.put(0x1f526, R.drawable.e0604);
        sEmojisMap.put(0x1f56f, R.drawable.e0605);
        sEmojisMap.put(0x1f5d1, R.drawable.e0606);
        sEmojisMap.put(0x1f6e2, R.drawable.e0607);
        sEmojisMap.put(0x1f4b8, R.drawable.e0608);
        sEmojisMap.put(0x1f4b5, R.drawable.e0615);
        sEmojisMap.put(0x1f4b4, R.drawable.e0616);
        sEmojisMap.put(0x1f4b7, R.drawable.e0617);
        sEmojisMap.put(0x1f4b6, R.drawable.e0620);
        sEmojisMap.put(0x1f4b0, R.drawable.e0621);
        sEmojisMap.put(0x1f4b3, R.drawable.e0622);
        sEmojisMap.put(0x1f48e, R.drawable.e0623);
        sEmojisMap.put(0x2696, R.drawable.e0624);
        sEmojisMap.put(0x1f527, R.drawable.e0625);
        sEmojisMap.put(0x1f528, R.drawable.e0688);
        sEmojisMap.put(0x2692, R.drawable.e0690);
        sEmojisMap.put(0x1f6e0, R.drawable.e0691);
        sEmojisMap.put(0x26cf, R.drawable.e0776);
        sEmojisMap.put(0x1f529, R.drawable.e0777);
        sEmojisMap.put(0x2699, R.drawable.e0778);
        sEmojisMap.put(0x26d3, R.drawable.e0779);
        sEmojisMap.put(0x1f52b, R.drawable.e0780);
        sEmojisMap.put(0x1f4a3, R.drawable.e0781);
        sEmojisMap.put(0x1f52a, R.drawable.e0782);
        sEmojisMap.put(0x1f5e1, R.drawable.e0783);
        sEmojisMap.put(0x2694, R.drawable.e1227);
        sEmojisMap.put(0x1f6e1, R.drawable.e1240);
        sEmojisMap.put(0x1f6ac, R.drawable.e1241);
        sEmojisMap.put(0x2620, R.drawable.e1242);
        sEmojisMap.put(0x26b0, R.drawable.e1271);
        sEmojisMap.put(0x26b1, R.drawable.e1272);
        sEmojisMap.put(0x1f3fa, R.drawable.e1273);
        sEmojisMap.put(0x1f52e, R.drawable.e1275);
        sEmojisMap.put(0x1f4ff, R.drawable.e1276);
        sEmojisMap.put(0x1f488, R.drawable.e1306);
        sEmojisMap.put(0x2697, R.drawable.e1307);
        sEmojisMap.put(0x1f52c, R.drawable.e1325);
        sEmojisMap.put(0x1f52d, R.drawable.e1326);
        sEmojisMap.put(0x1f573, R.drawable.e1336);
        sEmojisMap.put(0x1f48a, R.drawable.e1337);
        sEmojisMap.put(0x1f489, R.drawable.e1351);
        sEmojisMap.put(0x1f321, R.drawable.e1377);
        sEmojisMap.put(0x1f3f7, R.drawable.e1431);
        sEmojisMap.put(0x1f516, R.drawable.e1432);
        sEmojisMap.put(0x1f6bd, R.drawable.e1462);
        sEmojisMap.put(0x1f6bf, R.drawable.e1463);
        sEmojisMap.put(0x1f6c1, R.drawable.e1464);
        sEmojisMap.put(0x1f511, R.drawable.e1465);
        sEmojisMap.put(0x1f5dd, R.drawable.e1466);
        sEmojisMap.put(0x1f6cb, R.drawable.e1485);
        sEmojisMap.put(0x1f6cc, R.drawable.e1486);
        sEmojisMap.put(0x1f6cf, R.drawable.e1487);
        sEmojisMap.put(0x1f6aa, R.drawable.e1488);
        sEmojisMap.put(0x1f6ce, R.drawable.e1489);
        sEmojisMap.put(0x1f5bc, R.drawable.e1491);
        sEmojisMap.put(0x1f5fa, R.drawable.e1492);
        sEmojisMap.put(0x26f1, R.drawable.e1493);
        sEmojisMap.put(0x1f5ff, R.drawable.e1495);
        sEmojisMap.put(0x1f6cd, R.drawable.e1496);
        sEmojisMap.put(0x1f388, R.drawable.e1497);
        sEmojisMap.put(0x1f38f, R.drawable.e1498);
        sEmojisMap.put(0x1f380, R.drawable.e1499);
        sEmojisMap.put(0x1f381, R.drawable.e1500);
        sEmojisMap.put(0x1f38a, R.drawable.e1502);
        sEmojisMap.put(0x1f389, R.drawable.e1721);
        sEmojisMap.put(0x1f390, R.drawable.e1727);
        sEmojisMap.put(0x1f38c, R.drawable.e1778);
        sEmojisMap.put(0x1f3ee, R.drawable.e1779);
        sEmojisMap.put(0x2709, R.drawable.e1780);
        sEmojisMap.put(0x1f4e9, R.drawable.e1781);
        sEmojisMap.put(0x1f4e8, R.drawable.e1782);
        sEmojisMap.put(0x1f4e7, R.drawable.e1783);
        sEmojisMap.put(0x1f48c, R.drawable.e1784);
        sEmojisMap.put(0x1f4ee, R.drawable.e1785);
        sEmojisMap.put(0x1f4ea, R.drawable.e1791);
        sEmojisMap.put(0x1f4eb, R.drawable.e1792);
        sEmojisMap.put(0x1f4ec, R.drawable.e1793);
        sEmojisMap.put(0x1f4ed, R.drawable.e1794);
        sEmojisMap.put(0x1f4e6, R.drawable.e1795);
        sEmojisMap.put(0x1f4ef, R.drawable.e1796);
        sEmojisMap.put(0x1f4e5, R.drawable.e1797);
        sEmojisMap.put(0x1f4e4, R.drawable.e1798);
        sEmojisMap.put(0x1f4dc, R.drawable.e1802);
        sEmojisMap.put(0x1f4c3, R.drawable.e1803);
        sEmojisMap.put(0x1f4d1, R.drawable.e1804);
        sEmojisMap.put(0x1f4ca, R.drawable.e1805);
        sEmojisMap.put(0x1f4c8, R.drawable.e1806);
        sEmojisMap.put(0x1f4c9, R.drawable.e2012);
        sEmojisMap.put(0x1f4c4, R.drawable.e2015);
        sEmojisMap.put(0x1f4c5, R.drawable.e2017);
        sEmojisMap.put(0x1f4c6, R.drawable.e2018);
        sEmojisMap.put(0x1f5d3, R.drawable.e2019);
        sEmojisMap.put(0x1f4c7, R.drawable.e2021);
        sEmojisMap.put(0x1f5c3, R.drawable.e2022);
        sEmojisMap.put(0x1f5f3, R.drawable.e2047);
        sEmojisMap.put(0x1f5c4, R.drawable.e2210);
        sEmojisMap.put(0x1f4cb, R.drawable.e2211);
        sEmojisMap.put(0x1f5d2, R.drawable.e2212);
        sEmojisMap.put(0x1f4c1, R.drawable.e2213);
        sEmojisMap.put(0x1f4c2, R.drawable.e2214);
        sEmojisMap.put(0x1f5c2, R.drawable.e2215);
        sSoftbanksMap.put(0xe438, R.drawable.e2261);
        sEmojisMap.put(0x1f5de, R.drawable.e2229);
        sEmojisMap.put(0x1f4f0, R.drawable.e2230);
        sEmojisMap.put(0x1f4d3, R.drawable.e2243);
        sEmojisMap.put(0x1f4d5, R.drawable.e2259);
        sEmojisMap.put(0x1f4d7, R.drawable.e2261);
        sEmojisMap.put(0x1f4d8, R.drawable.e2272);
        sEmojisMap.put(0x1f4d9, R.drawable.e2303);
        sEmojisMap.put(0x1f4d4, R.drawable.e2304);
        sEmojisMap.put(0x1f4d2, R.drawable.e2305);
        sEmojisMap.put(0x1f4da, R.drawable.e2306);
        sEmojisMap.put(0x1f4d6, R.drawable.e2307);
        sEmojisMap.put(0x1f517, R.drawable.e2308);
        sEmojisMap.put(0x1f4ce, R.drawable.e2309);
        sEmojisMap.put(0x1f587, R.drawable.e2311);
        sEmojisMap.put(0x2702, R.drawable.e2314);
        sEmojisMap.put(0x1f4d0, R.drawable.e2315);
        sEmojisMap.put(0x1f4cf, R.drawable.e2316);
        sEmojisMap.put(0x1f4cc, R.drawable.e2317);
        sEmojisMap.put(0x1f4cd, R.drawable.e2320);
        sEmojisMap.put(0x1f3f3, R.drawable.e2324);
        sEmojisMap.put(0x1f3f4, R.drawable.e2325);
        sEmojisMap.put(0x1f510, R.drawable.e2326);
        sEmojisMap.put(0x1f512, R.drawable.e2328);
        sEmojisMap.put(0x1f513, R.drawable.e2331);
        sEmojisMap.put(0x1f50f, R.drawable.e2351);
        sEmojisMap.put(0x1f58a, R.drawable.e2352);
        sEmojisMap.put(0x1f58b, R.drawable.e2353);
        sEmojisMap.put(0x2712, R.drawable.e2381);
        sEmojisMap.put(0x1f4dd, R.drawable.e2382);
        sEmojisMap.put(0x270f, R.drawable.e2413);
        sEmojisMap.put(0x1f58d, R.drawable.e2414);
        sEmojisMap.put(0x1f58c, R.drawable.e2415);
        sEmojisMap.put(0x1f50d, R.drawable.e2416);
        ///ELECT



        /// SIGNES
        sEmojisMap.put(0x2764, R.drawable.e0001);
        sEmojisMap.put(0x1f49b, R.drawable.e0002);
        sEmojisMap.put(0x1f49a, R.drawable.e0003);
        sEmojisMap.put(0x1f499, R.drawable.e0004);
        sEmojisMap.put(0x1f49c, R.drawable.e0005);
        sEmojisMap.put(0x1f494, R.drawable.e0006);
        sEmojisMap.put(0x2763, R.drawable.e0007);
        sEmojisMap.put(0x1f495, R.drawable.e0008);
        sEmojisMap.put(0x1f49e, R.drawable.e0009);
        sEmojisMap.put(0x1f493, R.drawable.e0010);
        sEmojisMap.put(0x1f497, R.drawable.e0011);
        sEmojisMap.put(0x1f496, R.drawable.e0012);
        sEmojisMap.put(0x1f498, R.drawable.e0017);
        sEmojisMap.put(0x1f49d, R.drawable.e0018);
        sEmojisMap.put(0x1f49f, R.drawable.e0019);
        sEmojisMap.put(0x262e, R.drawable.e0020);
        sEmojisMap.put(0x271d, R.drawable.e0021);
        sEmojisMap.put(0x262a, R.drawable.e0022);
        sEmojisMap.put(0x1f549, R.drawable.e0023);
        sEmojisMap.put(0x2638, R.drawable.e0024);
        sEmojisMap.put(0x1f54e, R.drawable.e0025);
        sEmojisMap.put(0x262f, R.drawable.e0026);
        sEmojisMap.put(0x1f233, R.drawable.e0027);
        sEmojisMap.put(0x1f239, R.drawable.e0028);
        sEmojisMap.put(0x1f250, R.drawable.e0029);
        sEmojisMap.put(0x3299, R.drawable.e0030);
        sEmojisMap.put(0x3297, R.drawable.e0031);
        sEmojisMap.put(0x1f234, R.drawable.e0290);
        sEmojisMap.put(0x1f232, R.drawable.e0291);
        sEmojisMap.put(0x1f191, R.drawable.e0292);
        sEmojisMap.put(0x1f198, R.drawable.e0293);
        sEmojisMap.put(0x26d4, R.drawable.e0294);
        sEmojisMap.put(0x1f4db, R.drawable.e0295);
        sEmojisMap.put(0x1f6ab, R.drawable.e0296);
        sEmojisMap.put(0x274c, R.drawable.e0297);
        sEmojisMap.put(0x2b55, R.drawable.e0298);
        sEmojisMap.put(0x1f51e, R.drawable.e0299);
        sEmojisMap.put(0x1f4f5, R.drawable.e0300);
        sEmojisMap.put(0x1f6af, R.drawable.e0301);
        sEmojisMap.put(0x1f6b1, R.drawable.e0302);
        sEmojisMap.put(0x1f6b3, R.drawable.e0303);
        sEmojisMap.put(0x1f6b7, R.drawable.e0304);
        sEmojisMap.put(0x203c, R.drawable.e1228);
        sEmojisMap.put(0x2049, R.drawable.e1229);
        sEmojisMap.put(0x2757, R.drawable.e1230);
        sEmojisMap.put(0x2753, R.drawable.e1231);
        sEmojisMap.put(0x2755, R.drawable.e1232);
        sEmojisMap.put(0x2754, R.drawable.e1233);
        sEmojisMap.put(0x1f4af, R.drawable.e1234);
        sEmojisMap.put(0x1f6b8, R.drawable.e1235);
        sEmojisMap.put(0x1f506, R.drawable.e1236);
        sEmojisMap.put(0x1f505, R.drawable.e1237);
        sEmojisMap.put(0x1f531, R.drawable.e1238);
        sEmojisMap.put(0x1f530, R.drawable.e1262);
        sEmojisMap.put(0x267b, R.drawable.e1263);
        sEmojisMap.put(0x2733, R.drawable.e1264);
        sEmojisMap.put(0x2747, R.drawable.e1265);
        sEmojisMap.put(0x274e, R.drawable.e1266);
        sEmojisMap.put(0x2705, R.drawable.e1267);
        sEmojisMap.put(0x1f4b9, R.drawable.e1268);
        sEmojisMap.put(0x1f300, R.drawable.e1269);
        sEmojisMap.put(0x1f6be, R.drawable.e1277);
        sEmojisMap.put(0x1f6b0, R.drawable.e1278);
        sEmojisMap.put(0x1f17f, R.drawable.e1283);
        sEmojisMap.put(0x267f, R.drawable.e1284);
        sEmojisMap.put(0x1f6ad, R.drawable.e1285);
        sEmojisMap.put(0x1f202, R.drawable.e1286);
        sEmojisMap.put(0x24c2, R.drawable.e1287);
        sEmojisMap.put(0x1f6c2, R.drawable.e1288);
        sEmojisMap.put(0x1f6c4, R.drawable.e1289);
        sEmojisMap.put(0x1f6c5, R.drawable.e1290);
        sEmojisMap.put(0x1f6c3, R.drawable.e1291);
        sEmojisMap.put(0x1f6b9, R.drawable.e1292);
        sEmojisMap.put(0x1f6ba, R.drawable.e1293);
        sEmojisMap.put(0x1f6bc, R.drawable.e1294);
        sEmojisMap.put(0x1f6bb, R.drawable.e1295);
        sEmojisMap.put(0x1f6ae, R.drawable.e1296);
        sEmojisMap.put(0x1f51f, R.drawable.e1297);
        sEmojisMap.put(0x1f522, R.drawable.e1298);
        sEmojisMap.put(0x1f523, R.drawable.e1299);
        sEmojisMap.put(0x2b06, R.drawable.e1300);
        sEmojisMap.put(0x2b07, R.drawable.e1301);
        sEmojisMap.put(0x2b05, R.drawable.e1302);
        sEmojisMap.put(0x27a1, R.drawable.e1303);
        sEmojisMap.put(0x1f520, R.drawable.e1304);
        sEmojisMap.put(0x1f521, R.drawable.e1305);
        sEmojisMap.put(0x1f524, R.drawable.e1308);
        sEmojisMap.put(0x2197, R.drawable.e1309);
        sEmojisMap.put(0x2196, R.drawable.e1310);
        sEmojisMap.put(0x2198, R.drawable.e1311);
        sEmojisMap.put(0x2199, R.drawable.e1312);
        sEmojisMap.put(0x2194, R.drawable.e1313);
        sEmojisMap.put(0x2195, R.drawable.e1314);
        sEmojisMap.put(0x1f504, R.drawable.e1315);
        sEmojisMap.put(0x25c0, R.drawable.e1316);
        sEmojisMap.put(0x25b6, R.drawable.e1317);
        sEmojisMap.put(0x1f53c, R.drawable.e1318);
        sEmojisMap.put(0x1f53d, R.drawable.e1319);
        sEmojisMap.put(0x21a9, R.drawable.e1320);
        sEmojisMap.put(0x21aa, R.drawable.e1321);
        sEmojisMap.put(0x2139, R.drawable.e1322);
        sEmojisMap.put(0x23ea, R.drawable.e1323);
        sEmojisMap.put(0x23e9, R.drawable.e1324);
        sEmojisMap.put(0x23ed, R.drawable.e1325);
        sEmojisMap.put(0x23ef, R.drawable.e1326);
        sEmojisMap.put(0x23ee, R.drawable.e1327);
        sEmojisMap.put(0x23f8, R.drawable.e1328);
        sEmojisMap.put(0x23f9, R.drawable.e1329);
        sEmojisMap.put(0x23fa, R.drawable.e1330);
        sEmojisMap.put(0x23eb, R.drawable.e1339);
        sEmojisMap.put(0x23ec, R.drawable.e1340);
        sEmojisMap.put(0x2935, R.drawable.e1341);
        sEmojisMap.put(0x2934, R.drawable.e1342);
        sEmojisMap.put(0x1f197, R.drawable.e1343);
        sEmojisMap.put(0x1f500, R.drawable.e1344);
        sEmojisMap.put(0x1f501, R.drawable.e1345);
        sEmojisMap.put(0x1f502, R.drawable.e1346);
        sEmojisMap.put(0x1f195, R.drawable.e1347);
        sEmojisMap.put(0x1f199, R.drawable.e1348);
        sEmojisMap.put(0x1f192, R.drawable.e1349);
        sEmojisMap.put(0x1f193, R.drawable.e1350);
        sEmojisMap.put(0x1f196, R.drawable.e1351);
        sEmojisMap.put(0x1f4f6, R.drawable.e1352);
        sEmojisMap.put(0x1f3a6, R.drawable.e1353);
        sEmojisMap.put(0x1f201, R.drawable.e1354);
        sEmojisMap.put(0x1f4b2, R.drawable.e1355);
        sEmojisMap.put(0x1f4b1, R.drawable.e1356);
        sEmojisMap.put(0x00a9, R.drawable.e1357);
        sEmojisMap.put(0x00ae, R.drawable.e1358);
        sEmojisMap.put(0x2122, R.drawable.e1359);
        sEmojisMap.put(0x1f51d, R.drawable.e1360);
        sEmojisMap.put(0x1f51a, R.drawable.e1361);
        sEmojisMap.put(0x1f519, R.drawable.e1362);
        sEmojisMap.put(0x1f51b, R.drawable.e1363);
        sEmojisMap.put(0x1f51c, R.drawable.e1364);
        sEmojisMap.put(0x1f503, R.drawable.e1365);
        sEmojisMap.put(0x2716, R.drawable.e1366);
        sEmojisMap.put(0x2795, R.drawable.e1367);
        sEmojisMap.put(0x2796, R.drawable.e1368);
        sEmojisMap.put(0x2797, R.drawable.e1369);
        sEmojisMap.put(0x2714, R.drawable.e1370);
        sEmojisMap.put(0x2611, R.drawable.e1371);
        sEmojisMap.put(0x1f518, R.drawable.e1372);
        sEmojisMap.put(0x27b0, R.drawable.e1373);
        sEmojisMap.put(0x3030, R.drawable.e1374);
        sEmojisMap.put(0x1f4ae, R.drawable.e1375);
        sEmojisMap.put(0x25fc, R.drawable.e1385);
        sEmojisMap.put(0x25fb, R.drawable.e1386);
        sEmojisMap.put(0x25fe, R.drawable.e1387);
        sEmojisMap.put(0x25fd, R.drawable.e1388);
        sEmojisMap.put(0x25aa, R.drawable.e1389);
        sEmojisMap.put(0x25ab, R.drawable.e1390);
        sEmojisMap.put(0x1f53a, R.drawable.e1393);
        sEmojisMap.put(0x1f532, R.drawable.e1394);
        sEmojisMap.put(0x1f533, R.drawable.e1395);
        sEmojisMap.put(0x26ab, R.drawable.e1396);
        sEmojisMap.put(0x26aa, R.drawable.e1397);
        sEmojisMap.put(0x1f534, R.drawable.e1398);
        sEmojisMap.put(0x1f535, R.drawable.e1399);
        sEmojisMap.put(0x1f53b, R.drawable.e1400);
        sEmojisMap.put(0x2b1c, R.drawable.e1402);
        sEmojisMap.put(0x2b1b, R.drawable.e1403);
        sEmojisMap.put(0x1f536, R.drawable.e1404);
        sEmojisMap.put(0x1f537, R.drawable.e1405);
        sEmojisMap.put(0x1f538, R.drawable.e1406);
        sEmojisMap.put(0x1f539, R.drawable.e1407);
        sEmojisMap.put(0x1f50a, R.drawable.e1408);
        sEmojisMap.put(0x1f509, R.drawable.e1409);
        sEmojisMap.put(0x1f508, R.drawable.e1410);
        sEmojisMap.put(0x1f507, R.drawable.e1411);
        sEmojisMap.put(0x1f514, R.drawable.e1412);
        sEmojisMap.put(0x1f515, R.drawable.e1413);
        sEmojisMap.put(0x1f4e2, R.drawable.e1414);
        sEmojisMap.put(0x1f4e3, R.drawable.e1415);
        sEmojisMap.put(0x1f0cf, R.drawable.e1416);
        sEmojisMap.put(0x1f004, R.drawable.e1417);
        sEmojisMap.put(0x2660, R.drawable.e1418);
        sEmojisMap.put(0x2665, R.drawable.e1419);
        sEmojisMap.put(0x2663, R.drawable.e1420);
        sEmojisMap.put(0x2666, R.drawable.e1421);
        sEmojisMap.put(0x1f3b4, R.drawable.e1422);
        sEmojisMap.put(0x1f4ac, R.drawable.e1423);
        sEmojisMap.put(0x1f5ef, R.drawable.e1424);
        sEmojisMap.put(0x1f4ad, R.drawable.e1425);
        sEmojisMap.put(0x1f55b, R.drawable.e1426);
        sEmojisMap.put(0x1f567, R.drawable.e1427);
        sEmojisMap.put(0x1f550, R.drawable.e1428);
        sEmojisMap.put(0x1f55c, R.drawable.e1429);
        sEmojisMap.put(0x1f551, R.drawable.e1430);
        sEmojisMap.put(0x1f55d, R.drawable.e1724);
        sEmojisMap.put(0x1f552, R.drawable.e1725);
        sEmojisMap.put(0x1f55e, R.drawable.e1726);
        sEmojisMap.put(0x1f553, R.drawable.e1728);
        sEmojisMap.put(0x1f55f, R.drawable.e1730);
        sEmojisMap.put(0x1f554, R.drawable.e1770);
        sEmojisMap.put(0x1f560, R.drawable.e1771);
        sEmojisMap.put(0x1f555, R.drawable.e1772);
        sEmojisMap.put(0x1f556, R.drawable.e1773);
        sEmojisMap.put(0x1f557, R.drawable.e1774);
        sEmojisMap.put(0x1f558, R.drawable.e1775);
        sEmojisMap.put(0x1f559, R.drawable.e1776);
        sEmojisMap.put(0x1f55a, R.drawable.e1777);
        sEmojisMap.put(0x1f561, R.drawable.e1786);
        sEmojisMap.put(0x1f562, R.drawable.e1787);
        sEmojisMap.put(0x1f563, R.drawable.e1788);
        sEmojisMap.put(0x1f564, R.drawable.e1789);
        sEmojisMap.put(0x1f565, R.drawable.e1800);
    }

    private static boolean isSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    private static int getEmojiResource(Context context, int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    private static int getSoftbankEmojiResource(char c) {
        return sSoftbanksMap.get(c);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, 0, -1, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize, int index, int length) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, index, length, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment,
                                 int textSize, boolean useSystemDefault) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, 0, -1, useSystemDefault);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param index
     * @param length
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize, int index, int length, boolean useSystemDefault) {

        if (useSystemDefault) {
            return;
        }

        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            text.removeSpan(oldSpans[i]);
        }

        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            int icon = 0;
            char c = text.charAt(i);
            if (isSoftBankEmoji(c)) {
                icon = getSoftbankEmojiResource(c);
                skip = icon == 0 ? 0 : 1;
            }

            if (icon == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);

                if (unicode > 0xff) {
                    icon = getEmojiResource(context, unicode);
                }

                if (i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    //Non-spacing mark (Combining mark)
                    if (followUnicode == 0xfe0f) {
                        int followSkip = Character.charCount(followUnicode);
                        if (i + skip + followSkip < textLengthToProcess) {

                            int nextFollowUnicode = Character.codePointAt(text, i + skip + followSkip);
                            if (nextFollowUnicode == 0x20e3) {
                                int nextFollowSkip = Character.charCount(nextFollowUnicode);
                                int tempIcon = getKeyCapEmoji(unicode);

                                if (tempIcon == 0) {
                                    followSkip = 0;
                                    nextFollowSkip = 0;
                                } else {
                                    icon = tempIcon;
                                }
                                skip += (followSkip + nextFollowSkip);
                            }
                        }
                    } else if (followUnicode == 0x20e3) {
                        //some older versions of iOS don't use a combining character, instead it just goes straight to the second part
                        int followSkip = Character.charCount(followUnicode);

                        int tempIcon = getKeyCapEmoji(unicode);
                        if (tempIcon == 0) {
                            followSkip = 0;
                        } else {
                            icon = tempIcon;
                        }
                        skip += followSkip;

                    } else {
                        //handle other emoji modifiers
                        int followSkip = Character.charCount(followUnicode);

                        //TODO seems like we could do this for every emoji type rather than having that giant static map, maybe this is too slow?
                        String hexUnicode = Integer.toHexString(unicode);
                        String hexFollowUnicode = Integer.toHexString(followUnicode);

                        String resourceName = "emoji_" + hexUnicode + "_" + hexFollowUnicode;

                        int resourceId = 0;
                        if (sEmojisModifiedMap.containsKey(resourceName)) {
                            resourceId = sEmojisModifiedMap.get(resourceName);
                        } else if (!sInexistentEmojis.contains(resourceName)){
                            resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getApplicationContext().getPackageName());
                            if (resourceId != 0) {
                                sEmojisModifiedMap.put(resourceName, resourceId);
                            } else {
                              sInexistentEmojis.add(resourceName);
                            }
                        }

                        if (resourceId == 0) {
                            followSkip = 0;
                        } else {
                            icon = resourceId;
                        }
                        skip += followSkip;
                    }
                }
            }

            if (icon > 0) {
                text.setSpan(new EmojiconSpan(context, icon, emojiSize, emojiAlignment, textSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static int getKeyCapEmoji(int unicode) {
        int icon = 0;
        switch (unicode) {
            case 0x0023:
                icon = R.drawable.e0023;
                break;
            case 0x002a:
                icon = R.drawable.e0508;
                break;
            case 0x0030:
                icon = R.drawable.e0030;
                break;
            case 0x0031:
                icon = R.drawable.e1520;
                break;
            case 0x0032:
                icon = R.drawable.e1521;
                break;
            case 0x0033:
                icon = R.drawable.e1522;
                break;
            case 0x0034:
                icon = R.drawable.e1523;
                break;
            case 0x0035:
                icon = R.drawable.e1524;
                break;
            case 0x0036:
                icon = R.drawable.e1525;
                break;
            case 0x0037:
                icon = R.drawable.e1526;
                break;
            case 0x0038:
                icon = R.drawable.e1527;
                break;
            case 0x0039:
                icon = R.drawable.e1528;
                break;
            default:
                break;
        }
        return icon;
    }

}
