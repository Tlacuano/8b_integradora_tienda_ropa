<template>
  <b-container>
    <b-row class="p-4 main" no-gutters>
      <b-col cols="12" class="text-center my-auto">
        <img src="@/assets/image/404.png" alt="404" class="img-fluid"/>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "Error404",
  data() {
    return {};
  },
  methods: {}
};

</script>

<style>

.main{
  height: calc(100vh - 56px);
}

</style>