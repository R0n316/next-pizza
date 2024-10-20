import {Api} from "@/services/api-client";
import {UnauthorizedBanner} from "@/components/shared";

export default async function ProfilePage() {
    const response = await Api.user.getByEmail();
    console.log(response.status);
    if(response.status !== 200) {
        return <UnauthorizedBanner/>;
    }
    return <div>ДОСТУП РАЗРЕШЁН!!!!!!</div>

}