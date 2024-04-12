<template>
  <b-modal id="put-review-modal" title="Escribe una reseña" hide-footer hide-header centered @shown="initiModal" @hidden="resetModal">
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('put-review-modal')"/>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="12" class="text-center">
        <h1>Actualizar reseña</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4">
      <b-col cols="12">
        <b-form-group label="Calificación">
          <b-form-rating v-model="rating" variant="dark" size="lg" class="mb-2" name="rating" v-validate="'required'" ></b-form-rating>
          <span v-show="errors.has('rating')" class="text-danger">{{ errors.first('rating') }}</span>
        </b-form-group>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="12">
        <b-form-group label="Comentario" >
          <b-form-textarea v-model="comment" rows="3"  name="comment" v-validate="'required'"></b-form-textarea>
          <span v-show="errors.has('comment')" class="text-danger">{{ errors.first('comment') }}</span>
        </b-form-group>
      </b-col>
    </b-row>

    <b-row class="my-4">
      <b-col>
        <b-button class="main-button" @click="writeReview()" >
          Actualizar reseña
        </b-button>
      </b-col>
    </b-row>
  </b-modal>
</template>

<script>
import OrderService from "@/services/order/OrderService";
import ReviewService from "@/services/reviews/ReviewService";
import Vue from "vue";

export default {
  name: "PutReviewModal",
  props: {
    review: {}
  },
  data() {
    return {
      rating: 0,
      comment: '',
      idReview: '',

      orderHasProduct: {}
    }
  },
  methods:{

    async writeReview(){
      Promise.all([
        this.$validator.validateAll(),
      ]).then(async (result) => {
        if (result.every((value) => value)) {

          if(this.rating === 0){
            this.errors.add({field: 'rating', msg: 'La calificación es requerida'})
            return;
          }

          Vue.swal({
            title: '¿Deseas continuar?',
            text: "Tu reseña sera visible para todos los usuarios.",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: 'var(--black-base)',
            confirmButtonText: 'Sí, continuar',
            cancelButtonText: 'Cancelar',
          }).then(async (result) => {
            if (result.isConfirmed) {

              const payload = {
                idReview: this.idReview,
                comment: this.comment,
                assessment: this.rating,
              }

              const response = await ReviewService.putReviewService(payload)

              if(response){
                this.$bvModal.hide('write-review-modal')
                window.location.reload()
              }

            }
          });


        }
      })
    },


    async initiModal(){

      this.rating = this.review.assessment
      this.comment = this.review.comment
      this.idReview = this.review.idReview

    },
    resetModal(){
      this.rating = 0
      this.comment = ''
    }
  }
}
</script>

<style scoped>

</style>