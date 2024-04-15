<template>
    <section class="interface">
        <b-row>
            <b-col class="text-center">
                <h1>Favoritos</h1>
            </b-col>
        </b-row>

        <div v-if="wishlists.length > 0">
            <b-row class="mt-2 px-3">
                <b-col cols="12" lg="4">
                    <h4>{{ wishlists.length }} Favoritos</h4>
                </b-col>
            </b-row>
            <b-row>
                <b-col class="p-3" v-for="wishlistItem in wishlists" :key="wishlistItem.idWish" cols="12" sm="6" md="4" lg="3">
                    <b-card img-height="350px" :img-src="getImageUrl(wishlistItem.productGallery)" img-alt="Image" img-top tag="article" class="mb-2 selectable zoom-on-hover h-100" @click="selectProduct(wishlistItem.product.idProduct)">
                        <b-card-text class="text-left">
                            <b-row no-gutters>
                                <b-col cols="8">
                                    <p class="mb-0 font-weight-bold">
                                        {{ wishlistItem.product.productName }}
                                    </p>
                                </b-col>
                                <b-col class="text-right" cols="4">
                                    <b-button pill variant="light" class="wishlist-btn p-0" @click.stop="removeFromWishlist(wishlistItem)">
                                        <b-icon class="icon-container" icon="heart-fill" />
                                    </b-button>
                                </b-col>
                            </b-row>
                            <p class="mb-0"> {{ wishlistItem.product.price }} MXN</p>
                        </b-card-text>
                    </b-card>
                </b-col>
            </b-row>
        </div>

        <b-row v-else class="full-page" no-gutters>
            <b-col class="text-center mt-3" cols=12>
                <h3>No tienes favoritos</h3>
            </b-col>
        </b-row>
    </section>
</template>

<script>
import WishListService from "@/services/wish-list/WishListService";
import { codeCrypto } from "@/utils/security/cryptoJs";
import { showInfoAlert } from '../../components/alerts/alerts';

export default {
    name: "WishList",
    data() {
        return {
            wishlists: [],
        };
    },
    methods: {
        async getWishList() {
            this.showOverlay();
            const userEmail = this.$store.getters.getEmail;
            const response = await WishListService.getWishList(userEmail);
            this.wishlists = response.data;
            this.showOverlay();
        },
        selectProduct(idProduct) {
            const encodedId = codeCrypto(idProduct);
            this.$router.push({ name: "UserProductDetails", params: { id: encodedId } });
        },
        getImageUrl(productGallery) {
            if (productGallery && productGallery.length > 0) {
                return productGallery[0].image;
            } else {
                return "https://via.placeholder.com/300";
            }
        },
        async removeFromWishlist(wishlistItem) {
            await showInfoAlert(
                '¿Estás seguro?', 
                '¿Quieres quitar este producto de tus favoritos?', 'Sí, quitar',
                async () => {
                    await WishListService.deleteWishList(wishlistItem.idWish);

                    setTimeout(() => {
                        this.getWishList();
                    }, 1000);
                }

            );
        },

        showOverlay() {
            this.$store.dispatch('changeStatusOverlay');
        },
    },
    mounted() {
        this.getWishList();
    },
}
</script>

<style>
.interface {
    padding-bottom: calc(100px);
}

.wishlist-btn {
    background-color: transparent;
    border: none;
    font-size: 1.2rem;
}

.icon-container {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
