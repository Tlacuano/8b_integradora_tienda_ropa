<template>
  <b-container>
    <b-row class="p-4 main" no-gutters>
      <b-col cols="12" class="text-center my-auto">
        <img src="@/assets/image/500.png" alt="500" class="img-fluid"/>
      </b-col>
      <b-col class="text-center">
        <h1>El servidor no responde :(</h1>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
export default {
  name: "NoResponse",
  data() {
    return {};
  },
  methods: {}
};
</script>

<style scoped>
.main{
  height: calc(100vh - 56px);
}
</style>