<template>
  <div class="query" ref="query_ref">
    <el-container>
      <MenuBar></MenuBar>
      <el-header>
        <div style="text-align: center; font-size: 25px">
          <span>设备数据查询</span>
        </div>
        <div style="text-align: center; top: 30px">
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
        <el-table
          :data="pageData"
          style="width: 78%; margin: auto"
          :height="tableHeight"
          border
          stripe
          @sort-change="sortByColumn"
        >
          <el-table-column
            type="index"
            align="center"
            :index="getIndex"
          ></el-table-column>
          <el-table-column
            prop="clientId"
            label="设备ID"
            width="120"
            align="center"
            sortable="custom"
          >
          </el-table-column>
          <el-table-column
            prop="info"
            label="上报信息"
            width="300"
            align="center"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="value"
            label="设备数据"
            width="120"
            align="center"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="alert"
            label="告警状态"
            width="120"
            align="center"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="lng"
            label="经度"
            width="120"
            align="center"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="lat"
            label="维度"
            width="120"
            align="center"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="timestamp"
            label="上报时间"
            width="150"
            align="center"
            sortable
          >
          </el-table-column>
        </el-table>
      </el-main>
      <el-footer style="margin: auto">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import MenuBar from "@/components/MenuBar";
export default {
  data() {
    return {
      tableData: null,
      pageData: null,
      originData: null,
      currentPage: 1,
      pageSize: 10,
      total: null,
      keyword: null,
      tableHeight: 0,
    };
  },
  mounted() {
    this.getData();
    window.addEventListener("resize", this.screenAdapter); // 表格高度自适应
    this.screenAdapter(); // 初始化还要设置一次表格高度
  },
  methods: {
    init() {},
    async getData() {
      var { data: ret } = await this.$http.get("data-by-keyword", {
        params: {
          keyword: this.keyword,
        },
      });
      this.tableData = ret;
      this.originData = this.tableData;
      this.total = ret.length;
      this.flushPageData();
    },
    flushPageData() {
      var start = (this.currentPage - 1) * this.pageSize;
      var end = this.currentPage * this.pageSize;
      this.pageData = this.tableData.slice(start, end);
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
    // 关键字搜索功能，后端完成，重新getData
    search() {
      console.log(this.keyword);
      this.getData();
    },
    // 排序功能
    sortByColumn(column) {
      console.log(column);
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
      this.tableHeight = window.innerHeight - 250;
      // console.log(this.tableHeight);
    },
  },
  components: {
    MenuBar,
  },
};
</script>

<style lang="css">
.query {
  /* background-image: url("../assets/img/bg.jpg"); */
  /* color: #222733; */
}
</style>