package cn.hubindeveloper.rpc.client.Common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}
