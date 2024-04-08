<template>
  <b-row class="mt-4 mb-2 pb-5">
    <b-col>
      <hr class="pb-2"/>
      <b-row>
        <b-col class="text-center">
          <h3>Reseñas</h3>
        </b-col>
      </b-row>
      <b-row class="mt-4 ">
        <b-col cols="12" lg="5" class="mb-5">
            <b-row style="font-size: 1.5rem" class="text-center">
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="'fa-solid fa-star'" />
                {{reviewStats.countByRating["5"]}}
              </b-col>
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 4 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                {{reviewStats.countByRating["4"]}}
              </b-col>
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 3 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                {{reviewStats.countByRating["3"]}}
              </b-col>
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 2 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                {{reviewStats.countByRating["2"]}}
              </b-col>
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 1 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                {{reviewStats.countByRating["1"]}}
              </b-col>
              <b-col cols="12">
                <hr/>
              </b-col>
              <b-col cols="12">
                <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= reviewStats.averageRating ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
              </b-col>

            </b-row>
        </b-col>

        <b-col cols="12" lg="7" >
          <section v-if="reviews.length > 0" class="reviews-container">
            <b-row v-for="review in reviews" :key="review.idReview" class="highlight-on-hover">
              <b-col cols="1" class="pr-0 mt-1 mr-0 d-none d-lg-block">
                <b-avatar
                    :src="review.picture || null"
                    variant="dark"
                    size="2.5rem"
                />
              </b-col>
              <b-col cols="11">
                <b-row align-h="between">
                  <b-col cols="5" lg="9">
                    <b-row>
                      <b-col>
                        <b class="small">
                          {{ review.fullName }}
                        </b>
                      </b-col>
                    </b-row>
                    <b-row>
                      <b-col>
                        <small>
                          <font-awesome-icon v-for="index in 5" :key="`star_${index}_${review.idReview}`" :icon="index <= review.assessment ? 'fa-solid fa-star' : 'fa-regular fa-star'" />
                        </small>
                      </b-col>
                    </b-row>
                  </b-col>

                  <b-col cols="5" lg="2" class="text-right mt-2">
                    <b-row>
                      <b-col>
                        <small>{{ review.reviewDate }}</small>
                      </b-col>
                    </b-row>
                  </b-col>

                  <b-col cols="2" lg="1" v-if="review.email === $store.getters.getEmail">
                    <b-dropdown
                        variant="link-dark"
                        toggle-class="text-decoration-none"
                        no-caret
                    >
                      <template v-slot:button-content>
                        <font-awesome-icon icon="ellipsis-v"/>
                      </template>

                      <b-dropdown-item @click="editReview(review)">
                        Editar
                      </b-dropdown-item>
                      <b-dropdown-item @click="deleteReview(review)">
                        Eliminar
                      </b-dropdown-item>

                    </b-dropdown>
                  </b-col>
                </b-row>

                <b-row>
                  <b-col class="mt-3">
                    <small>{{ review.comment }}</small>
                  </b-col>
                </b-row>
              </b-col>

            </b-row>
          </section>
          <section v-else>
            <b-row>
              <b-col class="text-center">
                <p>No hay reseñas todavía</p>
              </b-col>
            </b-row>
          </section>

          <b-row class="mt-4 mb-5">
            <b-col>
              <b-card no-body class="selectable highlight-on-hover" @click="writeReview()">
                <span class="py-2 pl-3 text-secondary">
                  Escribir reseña
                </span>
              </b-card>
            </b-col>
          </b-row>

        </b-col>
      </b-row>
    </b-col>
    <WriteReviewModal :idProduct="idProduct"/>
  </b-row>
</template>

<script>
import ReviewService from "@/services/reviews/ReviewService";

export default {
  name: "ReviewsProduct",
  components: {
    WriteReviewModal: () => import("@/views/reviews/WriteReviewModal.vue")
  },
  props: {
    idProduct: String
  },
  data() {
    return {
      reviews: [],
      reviewStats: {
        countByRating: { 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 },
        averageRating: 0,
      },
    };
  },
  methods: {
    async getReviews() {
      const payload = {
        idProduct: this.idProduct
      };

      const response = await ReviewService.getReviewsByProductIdService(payload);
      this.reviews = response.data;

      this.calculateReviewStats();
      console.log(response);
    },

    calculateReviewStats() {
      let totalRating = 0;
      const countByRating = { 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 };

      this.reviews.forEach((review) => {
        if (review.assessment in countByRating) {
          countByRating[review.assessment]++;
          totalRating += review.assessment;
        }
      });

      const averageRating = totalRating / this.reviews.length;

      this.reviewStats = {
        countByRating,
        averageRating: Math.round(averageRating),
      };
    },

    async writeReview(){
      if(!this.$store.getters.isLoggedIn) {
        this.$bvModal.show("login-modal");
        return;
      }

      const payload = {
        idProduct: this.idProduct,
        email: this.$store.getters.getEmail
      }

      const response = await ReviewService.getComprobatToReviewService(payload)

      if(response){
        this.$bvModal.show("write-review-modal");
      }

    }
  },
  mounted() {
    this.getReviews();
  },
}

</script>

<style>
.reviews-container{
  height: calc(50vh - 10px);
}


</style>