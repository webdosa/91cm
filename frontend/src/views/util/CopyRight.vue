<template>
    <div>
        <b-button @click="test">test</b-button>
        <b-modal id="test-modal" size="lg" scrollable>
            <div v-for="message in messageList">
                <b-button class="button-margin" v-b-toggle.collapse-1 variant="primary" block>TEST</b-button>
                <b-collapse id="collapse-1" class="mt-2">
                <b-card>
                    <p class="card-text">{{message}}</p>
                </b-card>
                </b-collapse>
            </div>
        </b-modal>
    </div>
</template>
<script>
    export default {
        name: 'CopyRight',
        data() {
            return {
                messageList: ['1','2','3'],
            }
        },
        created: {
            makeTextMessage: function() {
                let message = ''
                for(let i=0;i<10;i++){
                    this.messageList.append(message+i)
                }
            }
        },
        methods: {
            test: function () {
                this.$bvModal.show("test-modal")
            }
        }

    }
</script>
<style scope>
    .button-margin{
        margin-bottom: 10px ;
        
    }
</style>