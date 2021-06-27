<template>
  <div class="config" ref="config_ref">
    <el-container>
      <MenuBar></MenuBar>
      <el-header>
        <div
          style="
            text-align: center;
            font-size: 25px;
            color: white;
            font-weight: bold;
          "
        >
          <span>设备配置界面</span>
        </div>
        <div style="text-align: center; top: 30px; color: white">
          <span>关键词查询：</span>
          <el-input
            placeholder="请输入关键字"
            prefix-icon="el-icon-search"
            style="width: 300px; margin-top: 10px"
            v-model="keyword"
            @keyup.enter.native="search"
          ></el-input>
        </div>
      </el-header>
      <el-main style="margin-top: 30px">
        <!-- 外层套一张卡片 -->
        <el-card style="width: 70%; padding: 3px 0; margin: auto; opacity: 0.9">
          <el-table
            :data="pageData"
            style="width: 80%; margin: auto; opacity: 1"
            :height="tableHeight"
            stripe
            @sort-change="sortByColumn"
            class="table"
          >
            <el-table-column
              type="index"
              align="center"
              :index="getIndex"
            ></el-table-column>
            <el-table-column
              prop="clientId"
              label="设备ID"
              width="200"
              align="center"
              sortable
            >
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.clientId"
                  :disabled="!scope.row.canEdit"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="设备名称"
              width="200"
              align="center"
              sortable
            >
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.name"
                  :disabled="!scope.row.canEdit"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="400" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                >
                  编辑
                </el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                >
                  删除
                </el-button>
                <el-button
                  :disabled="!scope.row.canEdit"
                  size="mini"
                  type="danger"
                  @click="confirmEdit(scope.$index, scope.row)"
                >
                  保存
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button size="mini" type="danger" @click="handleAdd()">
            增加
          </el-button>
        </el-card>
      </el-main>
      <el-footer style="margin: auto">
        <el-card style="opacity: 0.8">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            style="background-color: white"
          >
          </el-pagination>
        </el-card>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import MenuBar from "@/components/MenuBar";
export default {
  data() {
    return {
      deviceIds: null,
      tableData: null,
      pageData: null,
      originData: null,
      currentPage: 1,
      pageSize: 10,
      total: null,
      keyword: null,
      tableHeight: null,
    };
  },
  mounted() {
    this.getData();
    this.screenAdapter(); // 初始化还要设置一次表格高度
    window.addEventListener("resize", this.screenAdapter); // 表格高度自适应
  },
  methods: {
    init() {},
    async getData() {
      // 获取device ids
      var { data: ret } = await this.$http.get("device-ids");
      this.deviceIds = ret;
      this.tableData = this.deviceIds.map((item) => {
        return {
          clientId: item,
          name: item,
          canEdit: false, // 不可编辑
        };
      });
      this.originData = this.tableData;
      this.total = ret.length;
      this.flushPageData();
    },
    flushPageData() {
      var start = (this.currentPage - 1) * this.pageSize;
      var end = this.currentPage * this.pageSize;
      this.pageData = this.tableData.slice(start, end);
    },
    handleEdit(index, row) {
      row.canEdit = true;
    },
    confirmEdit(index, row) {
      row.canEdit = false;
      this.tableData[i] = {
        clientId: row.clientId,
        name: row.name,
        canEdit: row.canEdit,
      };
      this.originData[i] = this.tableData[i];
      this.flushPageData();
    },
    handleAdd() {
      this.tableData.push({
        clientId: "",
        name: "",
        canEdit: "",
      });
      this.originData = this.tableData;
      this.flushPageData();
    },
    handleDelete(index, row) {
      this.tableData.splice(index, 1);
      this.originData = this.tableData;
      this.total = this.tableData.length;
      this.flushPageData();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.flushPageData();
    },
    handleCurrentChange(val) {
      console.log(`当前页：${val}`);
      this.currentPage = val;
      this.flushPageData();
    },
    getIndex(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1;
    },
    // 关键字搜索功能
    search() {
      if (this.keyword == null || this.keyword == "") {
        this.tableData = this.originData;
      } else {
        var newData = new Array();
        for (var i = 0; i < this.tableData.length; i++) {
          if (
            this.tableData[i].clientId.search(this.keyword) != -1 ||
            this.tableData[i].name.search(this.keyword) != -1
          ) {
            newData.push(this.tableData[i]);
          }
        }
        this.tableData = newData;
      }

      this.total = this.tableData.length;
      this.flushPageData();
    },
    // 排序功能
    sortByColumn(column) {
      // console.log(column);
      // 保存原先的表数据
      this.originData = this.tableData;
      // 对用于显示的tableData进行排序
      var fieldName = column.prop;
      var type = column.order;
      // 如果type为null，恢复显示原数据
      if (type == null) {
        this.tableData = this.originData;
      }
      // 否则，显示排序后的数据，注意：sort的接口必须返回int值，不能返回bool值
      else {
        this.tableData = this.tableData.sort((a, b) => {
          if (type == "ascending") {
            return a[fieldName] < b[fieldName] ? 1 : -1;
          } else if (type == "descending") {
            return a[fieldName] > b[fieldName] ? 1 : -1;
          }
        });
      }
      this.flushPageData();
    },
    screenAdapter() {
      this.tableHeight = window.innerHeight * 0.5;
      // console.log(this.tableHeight);
    },
  },
  components: {
    MenuBar,
  },
};
</script>

<style lang="css">
.config {
  background-image: url("../assets/img/bg.png");
  height: 100%; /* 避免窗口大小发生变化时，底部出现白栏 */
  /* color: #222733; */
}
</style>