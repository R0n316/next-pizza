import {Api} from "@/services/api-client";
import {ProfileForm, UnauthorizedBanner} from "@/components/shared";
import {UserReadDto} from "@/services/model";

export default async function ProfilePage() {
    const response = await Api.user.getByEmail();
    if(response.status !== 200) {
        return <UnauthorizedBanner/>;
    }
    return <ProfileForm data={response.data as UserReadDto}/>;
}