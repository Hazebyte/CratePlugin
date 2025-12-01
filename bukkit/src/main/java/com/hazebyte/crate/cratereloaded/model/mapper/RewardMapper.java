package com.hazebyte.crate.cratereloaded.model.mapper;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = CommonMapperUtil.class)
public interface RewardMapper {

    @Mapping(target = "displayItem", qualifiedByName = "wrapOptional")
    @Mapping(source = "permissions", target = "exclusivePermissions")
    RewardV2 fromImplementation(RewardImpl reward);

    @Named("toRewardListV2")
    default List<RewardV2> fromImplementation(List<Reward> rewardList) {
        return fromRewardModel(rewardList).stream()
                .map(this::fromImplementation)
                .collect(Collectors.toList());
    }

    @Named("toRewardModelV2")
    default List<RewardImpl> fromRewardModel(List<Reward> rewardList) {
        return rewardList.stream()
                .filter(reward -> reward instanceof RewardImpl)
                .map(reward -> (RewardImpl) reward)
                .collect(Collectors.toList());
    }

    @Mapping(target = "displayItem", qualifiedByName = "unwrap")
    @Mapping(source = "exclusivePermissions", target = "permissions")
    RewardImpl toImplementation(RewardV2 reward);

    @Named("toRewardImpl")
    List<RewardImpl> toImplementation(List<RewardV2> rewardList);
}
