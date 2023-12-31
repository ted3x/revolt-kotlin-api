package app.revolt

import app.revolt.model.RevoltEmbedApiModel
import app.revolt.model.RevoltMessageApiModel
import app.revolt.model.auth.session.response.RevoltLoginApiResponse
import app.revolt.model.channel.RevoltChannelApiModel
import app.revolt.model.general.RevoltEmojiApiModel
import app.revolt.model.general.RevoltMetadataApiModel
import app.revolt.websocket.client.RevoltClientApiEvent
import app.revolt.websocket.server.RevoltServerApiEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

internal object RevoltApiJsonFactory {

    @OptIn(ExperimentalSerializationApi::class)
    fun create(): Json = Json {
        encodeDefaults = true
        explicitNulls = false
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        prettyPrint = false
        serializersModule = SerializersModule {
            polymorphic(RevoltLoginApiResponse::class) {
                subclass(RevoltLoginApiResponse.Success::class)
                subclass(RevoltLoginApiResponse.MFA::class)
                subclass(RevoltLoginApiResponse.Disabled::class)
            }
            polymorphic(RevoltMetadataApiModel::class){
                subclass(RevoltMetadataApiModel.File::class)
                subclass(RevoltMetadataApiModel.Audio::class)
                subclass(RevoltMetadataApiModel.Text::class)
                subclass(RevoltMetadataApiModel.Image::class)
                subclass(RevoltMetadataApiModel.Video::class)
            }
            polymorphic(RevoltMessageApiModel.System::class) {
                subclass(RevoltMessageApiModel.System.Text::class)
                subclass(RevoltMessageApiModel.System.UserAdded::class)
                subclass(RevoltMessageApiModel.System.UserRemoved::class)
                subclass(RevoltMessageApiModel.System.UserJoined::class)
                subclass(RevoltMessageApiModel.System.UserLeft::class)
                subclass(RevoltMessageApiModel.System.UserKicked::class)
                subclass(RevoltMessageApiModel.System.UserBanned::class)
                subclass(RevoltMessageApiModel.System.ChannelRenamed::class)
                subclass(RevoltMessageApiModel.System.ChannelDescriptionChanged::class)
                subclass(RevoltMessageApiModel.System.ChannelIconChanged::class)
                subclass(RevoltMessageApiModel.System.ChannelOwnershipChanged::class)
            }
            polymorphic(RevoltEmbedApiModel::class) {
                subclass(RevoltEmbedApiModel.None::class)
                subclass(RevoltEmbedApiModel.Website::class)
                subclass(RevoltEmbedApiModel.Image::class)
                subclass(RevoltEmbedApiModel.Video::class)
                subclass(RevoltEmbedApiModel.Text::class)
            }
            polymorphic(RevoltEmbedApiModel.Website.Special::class) {
                subclass(RevoltEmbedApiModel.Website.Special.None::class)
                subclass(RevoltEmbedApiModel.Website.Special.GIF::class)
                subclass(RevoltEmbedApiModel.Website.Special.Youtube::class)
                subclass(RevoltEmbedApiModel.Website.Special.Lightspeed::class)
                subclass(RevoltEmbedApiModel.Website.Special.Twitch::class)
                subclass(RevoltEmbedApiModel.Website.Special.Spotify::class)
                subclass(RevoltEmbedApiModel.Website.Special.Soundcloud::class)
                subclass(RevoltEmbedApiModel.Website.Special.Bandcamp::class)
                subclass(RevoltEmbedApiModel.Website.Special.Streamable::class)
            }
            polymorphic(RevoltClientApiEvent::class) {
                subclass(RevoltClientApiEvent.Authenticate::class)
                subclass(RevoltClientApiEvent.Ping::class)
                subclass(RevoltClientApiEvent.BeginTyping::class)
                subclass(RevoltClientApiEvent.EndTyping::class)
            }
            polymorphic(RevoltServerApiEvent::class) {
                subclass(RevoltServerApiEvent.Ready::class)
                subclass(RevoltServerApiEvent.Error::class)
                subclass(RevoltServerApiEvent.Authenticated::class)
                subclass(RevoltServerApiEvent.Bulk::class)
                subclass(RevoltServerApiEvent.Pong::class)
                subclass(RevoltServerApiEvent.UserUpdate::class)
            }
            polymorphic(RevoltChannelApiModel::class) {
                subclass(RevoltChannelApiModel.SavedMessages::class)
                subclass(RevoltChannelApiModel.DirectMessage::class)
                subclass(RevoltChannelApiModel.Group::class)
                subclass(RevoltChannelApiModel.TextChannel::class)
                subclass(RevoltChannelApiModel.VoiceChannel::class)
            }
            polymorphic(RevoltEmojiApiModel.Parent::class) {
                subclass(RevoltEmojiApiModel.Parent.Server::class)
                subclass(RevoltEmojiApiModel.Parent.Detached::class)
            }
        }
    }
}