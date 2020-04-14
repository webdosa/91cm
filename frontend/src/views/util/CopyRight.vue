<template>
        <b-modal id="copyRight-modal" size="lg" scrollable ok-only>
                <b-button class="button-margin" v-b-toggle.collapse-1 variant="outline-dark" block>Animate.css</b-button>
                <b-collapse id="collapse-1" class="mt-2">
                    <b-card>
                        <span>link :</span>
                        <b-link href="https://github.com/daneden/animate.css" style="color: blue;">https://github.com/daneden/animate.css</b-link>
                        <p class="card-text" >
                            The MIT License (MIT)
                            <br>
                            Copyright (c) 2019 Daniel Eden
                            <br>
                            Permission is hereby granted, free of charge, to any person obtaining a copy
                            of this software and associated documentation files (the "Software"), to deal
                            in the Software without restriction, including without limitation the rights
                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
                            copies of the Software, and to permit persons to whom the Software is
                            furnished to do so, subject to the following conditions:
                            <br>
                            The above copyright notice and this permission notice shall be included in all
                            copies or substantial portions of the Software.
                            <br>
                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
                            SOFTWARE.
                        </p>
                    </b-card>
                </b-collapse>
                <b-button class="button-margin" v-b-toggle.collapse-2 variant="outline-dark" block>SortableJS/Vue.Draggable</b-button>
                <b-collapse id="collapse-2" class="mt-2">
                    <b-card>
                        <span>link :</span>
                        <b-link href="https://github.com/SortableJS/Vue.Draggable" style="color: blue;">https://github.com/SortableJS/Vue.Draggable</b-link>
                        <p class="card-text" >
                            The MIT License (MIT)
                            <br>
                            Copyright (c) 2016-2019 David Desmaisons
                            <br>
                            Permission is hereby granted, free of charge, to any person obtaining a copy
                            of this software and associated documentation files (the "Software"), to deal
                            in the Software without restriction, including without limitation the rights
                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
                            copies of the Software, and to permit persons to whom the Software is
                            furnished to do so, subject to the following conditions:
                            <br>
                            The above copyright notice and this permission notice shall be included in all
                            copies or substantial portions of the Software.
                            <br>
                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
                            SOFTWARE.
                        </p>
                    </b-card>
                </b-collapse>

        </b-modal>
</template>
<script>
    export default {
        name: 'CopyRight',
        data() {
            return {
            }
        },
        created: {
        },
        methods: {
            testBtn: function (){
                const element = this.$refs.testBtn
                element.classList.add("animated","shake")
                element.addEventListener("animationend",()=>{
                    element.classList.remove("animated","shake")
                })
            },
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
                this.$bvModal.show("copyRight-modal")
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