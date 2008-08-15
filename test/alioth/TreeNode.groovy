/**
 *
 */
package alioth

class TreeNode {

    def left, right
    int item

    TreeNode(int item){
        this.item = item
    }

    static bottomUpTree(int item, int depth) {
        if (depth>0) {
            return new TreeNode(
                 bottomUpTree(2*item-1, depth-1)
               , bottomUpTree(2*item, depth-1)
               , item
            )
        } else {
            return new TreeNode(item)
        }
     }

    TreeNode(def left, def right, int item){
        this.left = left
        this.right = right
        this.item = item
    }

    int itemCheck(){
        if (left==null) return item
        else return item + left.itemCheck() - right.itemCheck()
    }

    static main(args) {
        int n = (args.length == 0) ? 10 : args[0].toInteger()

        int minDepth = 4
        int maxDepth = [ minDepth + 2, n].max()
        int stretchDepth = maxDepth + 1

        int check = (TreeNode.bottomUpTree(0,stretchDepth)).itemCheck()
        println "stretch tree of depth ${stretchDepth}\t check: ${check}"

        def longLivedTree = TreeNode.bottomUpTree(0, maxDepth)

        int depth=minDepth
        while (depth<=maxDepth) {
            int iterations = 1 << (maxDepth - depth + minDepth)
            check = 0
            for (i in 1..iterations) {
                check += (TreeNode.bottomUpTree(i,depth)).itemCheck()
                check += (TreeNode.bottomUpTree(-i,depth)).itemCheck()
            }
           println "${iterations*2}\t trees of depth ${depth}\t check: ${check}"
           depth+=2
        }

        println "long lived tree of depth ${maxDepth}\t check: ${longLivedTree.itemCheck()}"
     }
}
