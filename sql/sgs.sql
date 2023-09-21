/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50727 (5.7.27)
 Source Host           : localhost:3306
 Source Schema         : sgs

 Target Server Type    : MySQL
 Target Server Version : 50727 (5.7.27)
 File Encoding         : 65001

 Date: 21/09/2023 23:27:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_id` int(2) NULL DEFAULT NULL COMMENT '名称id',
  `num` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数字',
  `suit` int(1) NULL DEFAULT NULL COMMENT '花色（1黑桃2红桃3梅花4方片）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES (1, 1, '7', 1);
INSERT INTO `card` VALUES (2, 1, '8', 1);
INSERT INTO `card` VALUES (3, 1, '8', 1);
INSERT INTO `card` VALUES (4, 1, '9', 1);
INSERT INTO `card` VALUES (5, 1, '9', 1);
INSERT INTO `card` VALUES (6, 1, '10', 1);
INSERT INTO `card` VALUES (7, 1, '10', 1);
INSERT INTO `card` VALUES (8, 1, '10', 2);
INSERT INTO `card` VALUES (9, 1, '10', 2);
INSERT INTO `card` VALUES (10, 1, 'J', 2);
INSERT INTO `card` VALUES (11, 1, '2', 3);
INSERT INTO `card` VALUES (12, 1, '3', 3);
INSERT INTO `card` VALUES (13, 1, '4', 3);
INSERT INTO `card` VALUES (14, 1, '5', 3);
INSERT INTO `card` VALUES (15, 1, '6', 3);
INSERT INTO `card` VALUES (16, 1, '7', 3);
INSERT INTO `card` VALUES (17, 1, '8', 3);
INSERT INTO `card` VALUES (18, 1, '8', 3);
INSERT INTO `card` VALUES (19, 1, '9', 3);
INSERT INTO `card` VALUES (20, 1, '9', 3);
INSERT INTO `card` VALUES (21, 1, '10', 3);
INSERT INTO `card` VALUES (22, 1, '10', 3);
INSERT INTO `card` VALUES (23, 1, 'J', 3);
INSERT INTO `card` VALUES (24, 1, 'J', 3);
INSERT INTO `card` VALUES (25, 1, '6', 4);
INSERT INTO `card` VALUES (26, 1, '7', 4);
INSERT INTO `card` VALUES (27, 1, '8', 4);
INSERT INTO `card` VALUES (28, 1, '9', 4);
INSERT INTO `card` VALUES (29, 1, '10', 4);
INSERT INTO `card` VALUES (30, 1, 'K', 4);
INSERT INTO `card` VALUES (31, 2, '2', 2);
INSERT INTO `card` VALUES (32, 2, '2', 2);
INSERT INTO `card` VALUES (33, 2, 'K', 2);
INSERT INTO `card` VALUES (34, 2, '2', 4);
INSERT INTO `card` VALUES (35, 2, '2', 4);
INSERT INTO `card` VALUES (36, 2, '3', 4);
INSERT INTO `card` VALUES (37, 2, '4', 4);
INSERT INTO `card` VALUES (38, 2, '6', 4);
INSERT INTO `card` VALUES (39, 2, '5', 4);
INSERT INTO `card` VALUES (40, 2, '7', 4);
INSERT INTO `card` VALUES (41, 2, '8', 4);
INSERT INTO `card` VALUES (42, 2, '9', 4);
INSERT INTO `card` VALUES (43, 2, '10', 4);
INSERT INTO `card` VALUES (44, 2, 'J', 4);
INSERT INTO `card` VALUES (45, 2, 'J', 4);
INSERT INTO `card` VALUES (46, 3, '3', 2);
INSERT INTO `card` VALUES (47, 3, '4', 2);
INSERT INTO `card` VALUES (48, 3, '6', 2);
INSERT INTO `card` VALUES (49, 3, '7', 2);
INSERT INTO `card` VALUES (50, 3, '8', 2);
INSERT INTO `card` VALUES (51, 3, '9', 2);
INSERT INTO `card` VALUES (52, 3, 'Q', 2);
INSERT INTO `card` VALUES (53, 3, 'Q', 4);
INSERT INTO `card` VALUES (54, 4, 'A', 1);
INSERT INTO `card` VALUES (55, 4, 'A', 3);
INSERT INTO `card` VALUES (56, 4, 'A', 4);
INSERT INTO `card` VALUES (57, 5, '3', 1);
INSERT INTO `card` VALUES (58, 5, '4', 1);
INSERT INTO `card` VALUES (59, 5, 'Q', 1);
INSERT INTO `card` VALUES (60, 5, 'Q', 2);
INSERT INTO `card` VALUES (61, 5, '3', 3);
INSERT INTO `card` VALUES (62, 5, '4', 3);
INSERT INTO `card` VALUES (63, 6, '3', 1);
INSERT INTO `card` VALUES (64, 6, '4', 1);
INSERT INTO `card` VALUES (65, 6, 'J', 1);
INSERT INTO `card` VALUES (66, 6, '3', 4);
INSERT INTO `card` VALUES (67, 6, '4', 4);
INSERT INTO `card` VALUES (68, 7, '7', 2);
INSERT INTO `card` VALUES (69, 7, '8', 2);
INSERT INTO `card` VALUES (70, 7, '9', 2);
INSERT INTO `card` VALUES (71, 7, 'J', 2);
INSERT INTO `card` VALUES (72, 8, 'Q', 3);
INSERT INTO `card` VALUES (73, 8, 'K', 3);
INSERT INTO `card` VALUES (74, 9, 'J', 1);
INSERT INTO `card` VALUES (75, 9, 'K', 1);
INSERT INTO `card` VALUES (76, 9, 'A', 2);
INSERT INTO `card` VALUES (77, 9, 'K', 2);
INSERT INTO `card` VALUES (78, 9, 'Q', 3);
INSERT INTO `card` VALUES (79, 9, 'K', 3);
INSERT INTO `card` VALUES (80, 9, 'Q', 4);
INSERT INTO `card` VALUES (81, 10, '7', 1);
INSERT INTO `card` VALUES (82, 10, 'K', 1);
INSERT INTO `card` VALUES (83, 10, '7', 3);
INSERT INTO `card` VALUES (84, 11, 'A', 2);
INSERT INTO `card` VALUES (85, 12, 'A', 2);
INSERT INTO `card` VALUES (86, 13, '3', 2);
INSERT INTO `card` VALUES (87, 13, '4', 2);
INSERT INTO `card` VALUES (88, 14, '6', 1);
INSERT INTO `card` VALUES (89, 14, '6', 2);
INSERT INTO `card` VALUES (90, 14, '6', 3);
INSERT INTO `card` VALUES (91, 15, 'A', 1);
INSERT INTO `card` VALUES (92, 15, 'Q', 2);
INSERT INTO `card` VALUES (93, 16, 'A', 3);
INSERT INTO `card` VALUES (94, 16, 'A', 4);
INSERT INTO `card` VALUES (95, 17, '6', 1);
INSERT INTO `card` VALUES (96, 18, '2', 1);
INSERT INTO `card` VALUES (97, 19, '5', 4);
INSERT INTO `card` VALUES (98, 20, '5', 1);
INSERT INTO `card` VALUES (99, 21, 'Q', 1);
INSERT INTO `card` VALUES (100, 22, 'Q', 4);
INSERT INTO `card` VALUES (101, 23, '5', 2);
INSERT INTO `card` VALUES (102, 24, '2', 1);
INSERT INTO `card` VALUES (103, 25, '2', 1);
INSERT INTO `card` VALUES (104, 25, '2', 3);
INSERT INTO `card` VALUES (105, 26, '2', 3);
INSERT INTO `card` VALUES (106, 27, '5', 2);
INSERT INTO `card` VALUES (107, 28, 'K', 4);
INSERT INTO `card` VALUES (108, 29, 'K', 1);
INSERT INTO `card` VALUES (109, 30, 'K', 2);
INSERT INTO `card` VALUES (110, 31, '5', 3);
INSERT INTO `card` VALUES (111, 32, '5', 1);

-- ----------------------------
-- Table structure for card_parameter
-- ----------------------------
DROP TABLE IF EXISTS `card_parameter`;
CREATE TABLE `card_parameter`  (
  `id` int(2) NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of card_parameter
-- ----------------------------
INSERT INTO `card_parameter` VALUES (1, '杀', NULL, NULL);
INSERT INTO `card_parameter` VALUES (2, '闪', NULL, NULL);
INSERT INTO `card_parameter` VALUES (3, '桃', NULL, NULL);
INSERT INTO `card_parameter` VALUES (4, '决斗', '出牌阶段，对一名其他角色使用。由其开始，其与你轮流打出一张【杀】，直到其中一方未打出【杀】为止。未打出【杀】的一方受到另一方对其造成的1点伤害。', NULL);
INSERT INTO `card_parameter` VALUES (5, '过河拆桥', '出牌阶段，对区域里有牌的一名其他角色使用。你弃置其区域里的一张牌。', NULL);
INSERT INTO `card_parameter` VALUES (6, '顺手牵羊', '出牌阶段，对距离为1且区域里有牌的一名其他角色使用。你获得其区域里的一张牌。', NULL);
INSERT INTO `card_parameter` VALUES (7, '无中生有', '出牌阶段，对你使用。你摸两张牌。', NULL);
INSERT INTO `card_parameter` VALUES (8, '借刀杀人', '出牌阶段，对装备区里有武器牌且其攻击范围内有使用【杀】的目标的一名其他角色A使用。（选择目标时）你选择A攻击范围内的一名角色B（与A不同）。A需对B使用一张【杀】，否则将其装备区里的武器牌交给你。', NULL);
INSERT INTO `card_parameter` VALUES (9, '无懈可击', '一张锦囊牌生效前，抵消此牌对一名角色产生的效果，或抵消另一张【无懈可击】产生的效果。', NULL);
INSERT INTO `card_parameter` VALUES (10, '南蛮入侵', '出牌阶段，对所有其他角色使用。每名目标角色需打出一张【杀】，否则受到1点伤害。', NULL);
INSERT INTO `card_parameter` VALUES (11, '万箭齐发', '出牌阶段，对所有其他角色使用。每名目标角色需打出一张【闪】，否则受到1点伤害。', NULL);
INSERT INTO `card_parameter` VALUES (12, '桃园结义', '出牌阶段，对所有角色使用。每名目标角色回复1点体力。', NULL);
INSERT INTO `card_parameter` VALUES (13, '五谷丰登', '出牌阶段，对所有角色使用。（选择目标后）你从牌堆顶亮出等同于角色数量的牌，每名目标角色获得这些牌中（剩余的）的任意一张。', NULL);
INSERT INTO `card_parameter` VALUES (14, '乐不思蜀', '（延时类锦囊）出牌阶段，对一名其他角色使用。若判定结果不为红桃，跳过其出牌阶段。', NULL);
INSERT INTO `card_parameter` VALUES (15, '闪电', '（延时类锦囊）出牌阶段，对你（你是第一个目标，之后可能会不断改变）使用。若判定结果为黑桃2~9，则目标角色受到3点雷电伤害。若判定不为黑桃2~9，将之移动到其下家的判定区里。', NULL);
INSERT INTO `card_parameter` VALUES (16, '诸葛连弩', '锁定技，你于出牌阶段内使用【杀】无次数限制。', NULL);
INSERT INTO `card_parameter` VALUES (17, '青釭剑', '锁定技，每当你使用【杀】指定一名目标角色后，你无视其防具。', NULL);
INSERT INTO `card_parameter` VALUES (18, '雌雄双股剑', '每当你使用【杀】指定一名异性的目标角色后，你可以令其选择一项：1.弃置一张手牌；2.令你摸一张牌。', NULL);
INSERT INTO `card_parameter` VALUES (19, '贯石斧', '每当你使用的【杀】被目标角色使用的【闪】抵消时，你可以弃置两张牌，令此【杀】依然对其造成伤害。', NULL);
INSERT INTO `card_parameter` VALUES (20, '青龙偃月刀', '每当你使用的【杀】被目标角色使用的【闪】抵消时，你可以对其使用一张【杀】（无距离限制）。（如果有足够的【杀】，可以一直追杀下去，直到目标角色不使用【闪】或使用者无【杀】为止。）', NULL);
INSERT INTO `card_parameter` VALUES (21, '丈八蛇矛', '你可以将两张手牌当【杀】使用或打出。（如2张牌为红色，则视为红色的【杀】；如2张牌为黑色，视为黑色的【杀】；如2张牌为1红1黑，视为无色的【杀】。发动〖丈八蛇矛〗使用或打出的杀视为无点数性质。）', NULL);
INSERT INTO `card_parameter` VALUES (22, '方天画戟', '你使用的【杀】若是你最后的手牌，你可以额外选择至多两个目标。', NULL);
INSERT INTO `card_parameter` VALUES (23, '麒麟弓', '每当你使用【杀】对目标角色造成伤害时，你可以弃置其装备区里的一张坐骑牌。', NULL);
INSERT INTO `card_parameter` VALUES (24, '寒冰剑', '每当你使用【杀】对目标角色造成伤害时，若该角色有牌，你可以防止此伤害，改为依次弃置其两张牌。（不得弃掉对方判定区里的牌。）', NULL);
INSERT INTO `card_parameter` VALUES (25, '八卦阵', '每当你需要使用或打出一张【闪】时，你可以进行一次判定，若判定结果为红色，视为你使用或打出了一张【闪】。', NULL);
INSERT INTO `card_parameter` VALUES (26, '仁王盾', '锁定技，黑色的【杀】对你无效。（【决斗】过程中【仁王盾】无法触发。）', NULL);
INSERT INTO `card_parameter` VALUES (27, '赤兔', '锁定技，你与其他角色的距离-1。', NULL);
INSERT INTO `card_parameter` VALUES (28, '紫骍', '锁定技，你与其他角色的距离-1。', NULL);
INSERT INTO `card_parameter` VALUES (29, '大宛', '锁定技，你与其他角色的距离-1。', NULL);
INSERT INTO `card_parameter` VALUES (30, '爪黄飞电', '锁定技，其他角色与你的距离+1。', NULL);
INSERT INTO `card_parameter` VALUES (31, '的卢', '锁定技，其他角色与你的距离+1。', NULL);
INSERT INTO `card_parameter` VALUES (32, '绝影', '锁定技，其他角色与你的距离+1。', NULL);

-- ----------------------------
-- Table structure for general
-- ----------------------------
DROP TABLE IF EXISTS `general`;
CREATE TABLE `general`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `country` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国家',
  `blood` int(11) NOT NULL COMMENT '体力',
  `skill_ids` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能id组（,分隔）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of general
-- ----------------------------
INSERT INTO `general` VALUES (1, '刘备', '蜀', 4, '1,2');
INSERT INTO `general` VALUES (2, '张飞', '蜀', 4, '3');
INSERT INTO `general` VALUES (3, '关羽', '蜀', 4, '4');
INSERT INTO `general` VALUES (4, '诸葛亮', '蜀', 3, '5,6');
INSERT INTO `general` VALUES (5, '赵云', '蜀', 4, '7');
INSERT INTO `general` VALUES (6, '马超', '蜀', 4, '8,9');
INSERT INTO `general` VALUES (7, '黄月英', '蜀', 3, '10,11');
INSERT INTO `general` VALUES (8, '孙权', '吴', 4, '12,13');
INSERT INTO `general` VALUES (9, '甘宁', '吴', 4, '14');
INSERT INTO `general` VALUES (10, '吕蒙', '吴', 4, '15');
INSERT INTO `general` VALUES (11, '黄盖', '吴', 4, '16');
INSERT INTO `general` VALUES (12, '周瑜', '吴', 3, '17,18');
INSERT INTO `general` VALUES (13, '大乔', '吴', 3, '23,24');
INSERT INTO `general` VALUES (14, '陆逊', '吴', 3, '25,26');
INSERT INTO `general` VALUES (15, '孙尚香', '吴', 3, '27,28');
INSERT INTO `general` VALUES (16, '曹操', '魏', 4, '19,20');
INSERT INTO `general` VALUES (17, '司马懿', '魏', 3, '21,22');
INSERT INTO `general` VALUES (18, '夏侯惇', '魏', 4, '35');
INSERT INTO `general` VALUES (19, '张辽', '魏', 4, '29');
INSERT INTO `general` VALUES (20, '许褚', '魏', 4, '30');
INSERT INTO `general` VALUES (21, '郭嘉', '魏', 3, '31,32');
INSERT INTO `general` VALUES (22, '甄姬', '魏', 3, '33,34');
INSERT INTO `general` VALUES (23, '貂蝉', '群', 3, '36,37');
INSERT INTO `general` VALUES (24, '吕布', '群', 4, '38');
INSERT INTO `general` VALUES (25, '华佗', '群', 3, '39,40');

-- ----------------------------
-- Table structure for identity
-- ----------------------------
DROP TABLE IF EXISTS `identity`;
CREATE TABLE `identity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) NOT NULL COMMENT '人数',
  `zhu` int(11) NOT NULL COMMENT '主公',
  `zhong` int(11) NOT NULL COMMENT '忠臣',
  `fan` int(11) NOT NULL COMMENT '反贼',
  `nei` int(11) NOT NULL COMMENT '内奸',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of identity
-- ----------------------------
INSERT INTO `identity` VALUES (1, 4, 1, 1, 1, 1);
INSERT INTO `identity` VALUES (2, 5, 1, 1, 2, 1);
INSERT INTO `identity` VALUES (3, 6, 1, 1, 3, 1);
INSERT INTO `identity` VALUES (4, 7, 1, 2, 3, 1);
INSERT INTO `identity` VALUES (5, 8, 1, 2, 4, 1);
INSERT INTO `identity` VALUES (6, 6, 1, 1, 2, 2);
INSERT INTO `identity` VALUES (7, 8, 1, 2, 3, 2);
INSERT INTO `identity` VALUES (8, 9, 1, 3, 4, 1);
INSERT INTO `identity` VALUES (9, 10, 1, 3, 4, 2);
INSERT INTO `identity` VALUES (10, 2, 1, 0, 1, 0);

-- ----------------------------
-- Table structure for skill
-- ----------------------------
DROP TABLE IF EXISTS `skill`;
CREATE TABLE `skill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '介绍',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of skill
-- ----------------------------
INSERT INTO `skill` VALUES (1, '仁德', '出牌阶段，可以将任意数量的手牌以任意分配方式交给其他角色，若给出的牌张数不少于两张时，回复1点体力。', NULL);
INSERT INTO `skill` VALUES (2, '激将', '主公技，当玩家需要使用（或打出）一张【杀】时，可以发动激将。所有蜀势力角色按行动顺序依次选择是否“提供”打出一张【杀】，直到有一名角色或没有任何角色决定如此作时为止。', NULL);
INSERT INTO `skill` VALUES (3, '咆哮', '出牌阶段，可以使用任意数量的【杀】。', NULL);
INSERT INTO `skill` VALUES (4, '武圣', '可以将任意一张红色牌当【杀】使用或打出。若同时用到当前装备的红色装备效果时，不可把这张装备牌当【杀】来使用或打出。', NULL);
INSERT INTO `skill` VALUES (5, '观星', '回合开始阶段，可以观看牌堆顶的X张牌（X为存活角色的数量且最多为5），将其中任意数量的牌以任意顺序置于牌堆顶，其余以任意顺序置于牌堆底。', NULL);
INSERT INTO `skill` VALUES (6, '空城', '锁定技，当没有手牌时，不能成为【杀】或〔决斗〕的目标。当你在“决斗”过程中没有手牌无法打出【杀】时，仍然会受到【决斗】的伤害。', NULL);
INSERT INTO `skill` VALUES (7, '龙胆', '可以将手牌的【杀】当【闪】、【闪】当【杀】使用或打出。', NULL);
INSERT INTO `skill` VALUES (8, '马术', '锁定技，始终可视为装备有一匹-1马。马术的效果与装备－1马时效果一样，但仍然可以装备一匹－1马。', NULL);
INSERT INTO `skill` VALUES (9, '铁骑', '当使用【杀】指定一名角色为目标后，可以进行判定，若结果为红色，此【杀】不可被闪避。', NULL);
INSERT INTO `skill` VALUES (10, '集智', '每当使用一张非延时类锦囊时，（在它结算之前）可以立即摸一张牌。', NULL);
INSERT INTO `skill` VALUES (11, '奇才', '使用任何锦囊无距离限制。', NULL);
INSERT INTO `skill` VALUES (12, '制衡', '出牌阶段，可以弃掉任意数量的牌,然后摸取等量的牌，每回合限用一次。', NULL);
INSERT INTO `skill` VALUES (13, '救援', '主公技，锁定技，其他吴势力角色在濒死状态下使用〔桃〕时，额外回复1点体力。', NULL);
INSERT INTO `skill` VALUES (14, '奇袭', '出牌阶段，可以将任意黑色牌当【过河拆桥】使用。', NULL);
INSERT INTO `skill` VALUES (15, '克己', '若于出牌阶段未使用或打出过任何一张【杀】，可以跳过此回合的弃牌阶段。换言之，此回合手牌无上限。', NULL);
INSERT INTO `skill` VALUES (16, '苦肉', '出牌阶段，可以失去一点体力，然后摸两张牌。每回合中，可以多次使用苦肉。当失去最后一点体力时，优先结算濒死事件，当被救活后，才可以摸两张牌。换言之，可以用此技能自杀。', NULL);
INSERT INTO `skill` VALUES (17, '英姿', '摸牌阶段，可以额外摸一张牌。', NULL);
INSERT INTO `skill` VALUES (18, '反间', '出牌阶段，可以令另一名角色选择一种花色，抽取一张手牌并亮出，若此牌与所选花色不吻合，则对该角色造成1点伤害。然后不论结果，该角色都获得此牌。每回合限用一次。', NULL);
INSERT INTO `skill` VALUES (19, '奸雄', '可以立即获得造成伤害的牌。', NULL);
INSERT INTO `skill` VALUES (20, '护驾', '主公技，当需要使用（或打出）一张【闪】时，可以发动护驾。所有魏势力角色按行动顺序依次选择是否打出“提供”一张【闪】，直到有一名角色或没有任何角色决定如此做时为止。', NULL);
INSERT INTO `skill` VALUES (21, '反馈', '可以立即从造成伤害的来源处获得一张牌。一次无论受到多少点伤害，只能获得一张牌，若选择手牌则从对方手里随机抽取，选择面前的装备则任选。', NULL);
INSERT INTO `skill` VALUES (22, '鬼才', '在任意角色的判定牌生效前，可以打出一张手牌代替之。', NULL);
INSERT INTO `skill` VALUES (23, '国色', '出牌阶段，你可以将你的任意方块花色的牌当【乐不思蜀】使用。', NULL);
INSERT INTO `skill` VALUES (24, '流离', '当你成为【杀】的目标时，你可以弃一张牌，并将此【杀】转移给你攻击范围内的另一名角色（该角色不得是【杀】的使用者）。', NULL);
INSERT INTO `skill` VALUES (25, '谦逊', '锁定技，你不能成为【顺手牵羊】和【乐不思蜀】的目标。', NULL);
INSERT INTO `skill` VALUES (26, '连营', '每当你失去最后一张手牌时，可立即摸一张牌。', NULL);
INSERT INTO `skill` VALUES (27, '结姻', '出牌阶段，你可以弃两张手牌并选择一名受伤的男性角色：你和目标角色各回复1点体力。每回合限用一次。', NULL);
INSERT INTO `skill` VALUES (28, '枭姬', '当你失去一张装备区里的牌时，你可以立即摸两张牌。', NULL);
INSERT INTO `skill` VALUES (29, '突袭', '摸牌阶段，可以放弃摸牌，然后从至多两名（至少一名）角色的手牌里各抽取一张牌。摸牌阶段，一旦发动突袭，就不能从牌堆获得牌；只剩一名其他角色时，就只能选择这一名角色；若此时其他任何人都没有手牌，就不能发动突袭。', NULL);
INSERT INTO `skill` VALUES (30, '裸衣', '摸牌阶段，可以少摸一张牌；若如此做，该回合的出牌阶段，使用【杀】或【决斗】（为伤害来源时）造成的伤害+1。', NULL);
INSERT INTO `skill` VALUES (31, '天妒', '在判定牌生效时，可以立即获得它。判定牌生效时即判定结果决定后。', NULL);
INSERT INTO `skill` VALUES (32, '遗计', '每受到1点伤害，可摸两张牌，将其中的一张交给任意一名角色，然后将另一张交给任意一名角色。', NULL);
INSERT INTO `skill` VALUES (33, '倾国', '你可以将你的黑色手牌当【闪】使用（或打出）', NULL);
INSERT INTO `skill` VALUES (34, '洛神', '回合开始阶段，你可以进行判定：若为黑色，立即获得此生效后的判定牌，并可以再次使用洛神――如此反复，直到出现红色或你不愿意判定了为止。', NULL);
INSERT INTO `skill` VALUES (35, '刚烈', '每受到一次伤害，可进行一次判定：若结果不为红桃，则目标来源必须进行二选一：弃两张手牌或受到对其造成的1点伤害。', NULL);
INSERT INTO `skill` VALUES (36, '离间', '出牌阶段，你可以弃一张牌并选择两名男性角色。若如此作，视为其中一名男性角色对另一名男性角色使用一张【决斗】。（此【决斗】不能被【无懈可击】响应）。每回合限用一次。', NULL);
INSERT INTO `skill` VALUES (37, '闭月', '回合结束阶段，可摸一张牌。', NULL);
INSERT INTO `skill` VALUES (38, '无双', '锁定技，你使用【杀】时，目标角色需连续使用两张【闪】才能抵消；与你进行【决斗】的角色每次需连续打出两张【杀】。若对方只有一张【闪】或【杀】则即便使用（打出）了也无效。', NULL);
INSERT INTO `skill` VALUES (39, '急救', '你的回合外，你可以将你的任意红色牌当【桃】使用。', NULL);
INSERT INTO `skill` VALUES (40, '青囊', '出牌阶段，你可以主动弃掉一张手牌，令任一目标角色回复1点体力。每回合限用一次。', NULL);

SET FOREIGN_KEY_CHECKS = 1;
