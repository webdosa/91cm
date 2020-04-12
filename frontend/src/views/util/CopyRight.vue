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
        <b-button @click="shakeTest">test</b-button>
        <div :class="{swing: check}">
            test
        </div>
        <b-button @click="testAni">test</b-button>
        <h1 ref="swings">Example</h1>
    </div>
</template>
<script>
    export default {
        name: 'CopyRight',
        data() {
            return {
                swingChecker: false,
                check: false,
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
            testAni : function(){
                const node = this.$refs.swings
                node.classList.add("animated","shake")
                function handleAnimationEnd(){
                    node.classList.remove("animated","shake")
                    node.removeEventListener("animationend", handleAnimationEnd)
                    if (typeof callback === 'function')
                        callback()
                }
                node.addEventListener("animationend", handleAnimationEnd)
            },
            test: function () {
                this.$bvModal.show("test-modal")
            },
            shakeTest: function(){
            this.check = true
            console.log("test")
            this.checkmakeFalse()
            },
            checkmakeFalse: function(){
                const v = this
                setTimeout(() => {
                v.check = false
                console.log("callback test")
            }, 1000);
            },
            sleep(delay){
                let start = new Date().getTime()
                while(new Date().getTime() < start + delay);
            }
        }

    }
</script>
<style scope>
@import "../../assets/css/animate.css";
    .button-margin{
        margin-bottom: 10px ;
        
    }
</style>