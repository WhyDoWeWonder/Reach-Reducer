package com.lordoflightning.reachreducer.config;

public class ConfigInstance {

    private float attackReachDistance;
    private float blockReachDistance;
    private float creativeAttackReachDistance;
    private float creativeBlockReachDistance;


    public ConfigInstance() {
        attackReachDistance = 3.0f;
        blockReachDistance = 4.5f;
        creativeAttackReachDistance = 6.0f;
        creativeBlockReachDistance = 5.0f;
    }

    public float getAttackReachDistance() {
        return attackReachDistance;
    }

    public float getBlockReachDistance() {
        return blockReachDistance;
    }

    public float getCreativeAttackReachDistance() {
        return creativeAttackReachDistance;
    }

    public float getCreativeBlockReachDistance() {
        return creativeBlockReachDistance;
    }

    public void setAttackReachDistance(float attackReachDistance) {
        this.attackReachDistance = getValidReach(3.0f, attackReachDistance);
    }

    public void setBlockReachDistance(float blockReachDistance) {
        this.blockReachDistance = getValidReach(4.5f, blockReachDistance);
    }

    public void setCreativeAttackReachDistance(float creativeAttackReachDistance) {
        this.creativeAttackReachDistance = getValidReach(6.0f, creativeAttackReachDistance);
    }

    public void setCreativeBlockReachDistance(float creativeBlockReachDistance) {
        this.creativeBlockReachDistance = getValidReach(5.0f, creativeBlockReachDistance);
    }

    public float getValidReach(float max, float reachDistance) {
        if (reachDistance < 0f)
            reachDistance = 0f;
        else if (reachDistance > max)
            reachDistance = max;
        return reachDistance;
    }
}
