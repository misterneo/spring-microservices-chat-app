export const formatUsername = (name) => {
  return "@" + name.replace(" ", "").toLowerCase();
};

export const createAvatar = (name) => {
  return `https://source.boringavatars.com/beam/120/${name}`;
};

export const messageMapper = (message) => {
  return {
    id: message.id,
    user: {
      id: message.userId,
      name: formatUsername(message.fullName),
      avatar: createAvatar(message.userId),
      isOnline: true,
    },
    content: message.content,
    createdAt: message.createdAt,
  };
};
